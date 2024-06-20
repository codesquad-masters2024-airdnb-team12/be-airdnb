package com.airbnb.domain.booking.service;

import static com.airbnb.domain.booking.entity.BookingStatus.COMPLETED;
import static com.airbnb.domain.booking.entity.BookingStatus.CONFIRMED;
import static com.airbnb.domain.booking.entity.BookingStatus.USING;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.booking.dto.request.BookingCreateRequest;
import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.booking.entity.BookingStatus;
import com.airbnb.domain.booking.repository.BookingRepository;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.domain.payment.dto.AmountResult;
import com.airbnb.domain.payment.entity.Card;
import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.payment.util.AmountCalculationUtil;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import com.airbnb.domain.policy.service.PolicyService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;
    private final AmountCalculationUtil amountCalculationUtil;
    private final PolicyService policyService;

    @Transactional
    public BookingResponse create(String guestKey, Long accommodationId, BookingCreateRequest request) {
        Member guest = memberRepository.findByEmail(guestKey).orElseThrow();
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();

        Booking entity = request.toEntity(guest, accommodation);
        AmountResult amountResult = getAmountResult(entity);
        Card guestCard = Card.valueOf(request.getCardName());

        Payment newPayment = Payment.builder().booking(entity).amountResult(amountResult).card(guestCard).build();
        entity.setPayment(newPayment);
        Booking newBooking = bookingRepository.save(entity);

        return BookingResponse.from(newBooking);
    }

    public BookingListResponse getAllByGuestKeyAndStatus(String guestKey, BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByGuestEmailAndStatus(guestKey, status);
        return BookingListResponse.from(bookings);
    }

    public BookingListResponse getAllByHostKeyAndStatus(String hostKey, BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByAccommodationHostEmailAndStatus(hostKey, status);
        return BookingListResponse.from(bookings);
    }

    public BookingResponse getById(Long bookingId) {
        Booking targetBooking = bookingRepository.findById(bookingId).orElseThrow();

        // 현재 로그인된 사용자가 조회하고자 하는 예약의 게스트 또는 호스트와 일치하는지 검증
        if (!validateHostAuth(targetBooking) && !validateGuestAuth(targetBooking)) {
            throw new IllegalArgumentException("조회 권한이 없습니다.");
        }

        return BookingResponse.from(targetBooking);
    }

    @Transactional
    public void updateStatusByCheckIn() {
        bookingRepository.findByCheckInEqualsAndStatus(LocalDate.now(), CONFIRMED)
            .forEach(booking -> booking.changeStatus(USING));
    }

    @Transactional
    public void updateStatusByCheckOut() {
        bookingRepository.findByCheckOutEqualsAndStatus(LocalDate.now(), USING)
            .forEach(booking -> booking.changeStatus(COMPLETED));
    }

    @Transactional
    public BookingResponse approve(Long bookingId) {
        Booking targetBooking = bookingRepository.findById(bookingId).orElseThrow();

        // 현재 로그인된 호스트가 승인하고자 하는 예약의 호스트와 일치하는지 검증
        if (!validateHostAuth(targetBooking)) {
            throw new IllegalArgumentException("승인 권한이 없습니다.");
        }

        targetBooking.approve();
        return BookingResponse.from(targetBooking);
    }

    @Transactional
    public BookingResponse cancel(Long bookingId) {
        Booking targetBooking = bookingRepository.findById(bookingId).orElseThrow();

        // 현재 로그인된 호스트가 취소하고자 하는 예약의 게스트와 일치하는지 검증
        if (!validateGuestAuth(targetBooking)) {
            throw new IllegalArgumentException("취소 권한이 없습니다.");
        }

        targetBooking.cancel();
        return BookingResponse.from(targetBooking);
    }

    @Transactional
    public BookingResponse reject(Long bookingId) {
        Booking targetBooking = bookingRepository.findById(bookingId).orElseThrow();

        // 현재 로그인된 호스트가 거절하고자 하는 예약의 호스트와 일치하는지 검증
        if (!validateHostAuth(targetBooking)) {
            throw new IllegalArgumentException("거절 권한이 없습니다.");
        }

        targetBooking.reject();
        return BookingResponse.from(targetBooking);
    }

    private boolean validateHostAuth(Booking booking) {
        return booking.isHost(getLoggedInMemberKey());
    }

    private boolean validateGuestAuth(Booking booking) {
        return booking.isGuest(getLoggedInMemberKey());
    }

    private String getLoggedInMemberKey() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private AmountResult getAmountResult(Booking booking) {
        LocalDate bookingCreatedDate = booking.getCreatedAt().toLocalDate();
        FeePolicy feePolicy = policyService.getFeePolicyByDate(bookingCreatedDate);
        DiscountPolicy discountPolicy = policyService.getDiscountPolicyByDate(bookingCreatedDate);
        return amountCalculationUtil.getAmountResult(booking, feePolicy, discountPolicy);
    }
}