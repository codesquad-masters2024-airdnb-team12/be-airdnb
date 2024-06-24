package com.airbnb.domain.booking.service;

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
import com.airbnb.domain.common.AmountResult;
import com.airbnb.domain.common.Card;
import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.policy.repository.DiscountPolicyRepository;
import com.airbnb.domain.policy.repository.FeePolicyRepository;
import com.airbnb.global.util.AmountCalculationUtil;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.airbnb.domain.common.BookingStatus.CONFIRMED;
import static com.airbnb.domain.common.BookingStatus.REQUESTED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;
    private final FeePolicyRepository feePolicyRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

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

        if(accommodation.getHost().getId().equals(guestId)){
            throw new IllegalArgumentException("자신의 숙소는 예약할 수 없습니다.");
        }

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
        List<Booking> bookings;

        if(status == null || status.isEmpty()) {
            bookings = bookingRepository.findByGuestId(guestId);
        } else {
            bookings = bookingRepository.findByGuestIdAndStatus(guestId, BookingStatus.from(status));
        }
        return BookingListResponse.from(bookings);
    }

    public BookingResponse getById(Long guestId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        if (!booking.isGuest(guestId) && !booking.isHost(guestId)) {
            throw new IllegalArgumentException("조회 권한이 없습니다.");
        }

        return BookingResponse.from(booking);
    }

    @Transactional
    public BookingResponse cancel(Long guestId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.isGuest(guestId) && !booking.isHost(guestId)) {
            throw new IllegalArgumentException("예약 취소 권한이 없습니다.");
        }

        if (booking.getStatus() != REQUESTED && booking.getStatus() != CONFIRMED) {
            throw new IllegalArgumentException("요청 상태나 승인 상태인 예약만 취소할 수 있습니다.");
        }

        if (!booking.getCheckIn().isAfter(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("체크인 하루 전까지만 취소할 수 있습니다.");
        }

        booking.cancel();

        return BookingResponse.from(booking);
    }

    private AmountResult getAmountResult(Booking booking) {
        // 예약하는 현재시간 기준으로 수수료 정책 조회
        LocalDate bookingCreatedDate = LocalDate.now();
        FeePolicy feePolicy = feePolicyRepository.findValidFeePolicy(bookingCreatedDate).orElseThrow();
        DiscountPolicy discountPolicy = discountPolicyRepository.findValidDiscountPolicy(bookingCreatedDate).orElseThrow();
        return AmountCalculationUtil.getAmountResult(booking, feePolicy, discountPolicy);
    }
}