package com.airbnb.domain.booking.service;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.booking.dto.request.BookingCreateRequest;
import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.BookingStatus;
import com.airbnb.domain.booking.repository.BookingRepository;
import com.airbnb.domain.common.PaymentStatus;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.domain.common.AmountResult;
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

import static com.airbnb.domain.common.BookingStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;
    private final PolicyService policyService;

    // 예약
    @Transactional
    public BookingResponse create(Long guestId, Long accommodationId, BookingCreateRequest request) {
        if (!request.isCheckInAfterToday()) {
            throw new IllegalArgumentException("오늘 이후의 날짜만 예약이 가능합니다.");
        }

        if(!request.isCheckOutAfterCheckIn()) {
            throw new IllegalArgumentException("체크아웃은 체크인 이후여야 합니다.");
        }

        if(bookingRepository.isOverlapBookingExists(accommodationId, request.getCheckIn(), request.getCheckOut())) {
            throw new IllegalArgumentException("해당 기간에 이미 예약된 숙소입니다.");
        }

        Member guest = memberRepository.findById(guestId).orElseThrow();
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();

        if (!request.isUnderMaxGuests(accommodation.getMaxGuests())) {
            throw new IllegalArgumentException("숙소의 예약 가능 최대 인원을 초과했습니다.");
        }

        Booking entity = request.toEntity(guest, accommodation);
        AmountResult amountResult = getAmountResult(entity);
        Card guestCard = Card.of(request.getCardName());

        Payment newPayment = Payment.builder().booking(entity).amountResult(amountResult).card(guestCard).build();
        entity.setPayment(newPayment);
        Booking newBooking = bookingRepository.save(entity);

        return BookingResponse.from(newBooking);
    }

    public BookingListResponse getAllByGuestIdAndStatus(Long guestId, String status) {
        List<Booking> bookings = bookingRepository.findByGuestIdAndStatus(guestId, BookingStatus.of(status));
        return BookingListResponse.from(bookings);
    }

    public BookingListResponse getAllByHostIdAndStatus(Long hostId, String status) {
        List<Booking> bookings = bookingRepository.findByAccommodationHostIdAndStatus(
                hostId, BookingStatus.of(status));
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

        if (bookingStatus.equals(CONFIRMED)) {
            targetBooking.getPayment().changeStatus(PaymentStatus.COMPLETED);
        } else if (bookingStatus.equals(CANCELED) || bookingStatus.equals(REJECTED)) {
            targetBooking.getPayment().changeStatus(PaymentStatus.WITHDRAWN);
        }

        // TODO: 게스트 예약취소
        // TODO: 허용, 거절은 호스트만 가능
        // TODO: 이용중, 이용완료, 예약 요청으로 상태 변경은 불가능

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