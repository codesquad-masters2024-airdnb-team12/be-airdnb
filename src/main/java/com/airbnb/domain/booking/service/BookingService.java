package com.airbnb.domain.booking.service;

import static com.airbnb.domain.common.BookingStatus.COMPLETED;
import static com.airbnb.domain.common.BookingStatus.CONFIRMED;
import static com.airbnb.domain.common.BookingStatus.USING;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.booking.dto.request.BookingCreateRequest;
import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.BookingStatus;
import com.airbnb.domain.booking.repository.BookingRepository;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.domain.payment.dto.AmountResult;
import com.airbnb.domain.common.Card;
import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.global.util.AmountCalculationUtil;
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
    private final PolicyService policyService;

    @Transactional
    public BookingResponse create(String guestKey, Long accommodationId, BookingCreateRequest request) {
        Member guest = memberRepository.findByEmail(guestKey).orElseThrow();
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();

        // TODO: 최대인원 체크
        // TODO: 필수입력값들은 wrapper로 notNull 체크
        // TODO: 예약 일자는 오늘 이후여야 함
        // TODO: 예약이 이미 있을 경우 예약 불가해야 함

        Booking entity = request.toEntity(guest, accommodation);
        AmountResult amountResult = getAmountResult(entity);
        Card guestCard = Card.of(request.getCardName());

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
        if (!validateHostAuth(targetBooking) || !validateGuestAuth(targetBooking)) {
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
    public BookingResponse changeStatus(Long bookingId, String status) {
        Booking targetBooking = bookingRepository.findById(bookingId).orElseThrow();
        BookingStatus bookingStatus = BookingStatus.of(status);

        if (!validateHostAuth(targetBooking)) {
            throw new IllegalArgumentException("예약 상태 수정 권한이 없습니다.");
        }

        targetBooking.changeStatus(bookingStatus);
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
        // 예약하는 현재시간 기준으로 수수료 정책 조회
        LocalDate bookingCreatedDate = LocalDate.now();
        FeePolicy feePolicy = policyService.getFeePolicyByDate(bookingCreatedDate);
        DiscountPolicy discountPolicy = policyService.getDiscountPolicyByDate(bookingCreatedDate);
        return AmountCalculationUtil.getAmountResult(booking, feePolicy, discountPolicy);
    }
}