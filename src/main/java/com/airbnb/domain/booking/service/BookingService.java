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
    public BookingResponse create(Long guestId, Long accommodationId, BookingCreateRequest request) {
        Member guest = memberRepository.findById(guestId).orElseThrow();
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();

        Booking entity = request.toEntity(guest, accommodation);
        AmountResult amountResult = getAmountResult(entity);
        Card guestCard = Card.valueOf(request.getCardName());

        Payment newPayment = Payment.builder().booking(entity).amountResult(amountResult).card(guestCard).build();
        entity.setPayment(newPayment);
        Booking newBooking = bookingRepository.save(entity);

        return BookingResponse.from(newBooking);
    }

    public BookingListResponse getAllByGuestIdAndStatus(Long guestId, BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByGuestIdAndStatus(guestId, status);
        return BookingListResponse.from(bookings);
    }

    public BookingListResponse getAllByHostIdAndStatus(Long hostId, BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByAccommodationHostIdAndStatus(hostId, status);
        return BookingListResponse.from(bookings);
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
        targetBooking.approve();

        return BookingResponse.from(targetBooking);
    }

    @Transactional
    public BookingResponse cancel(Long bookingId) {
        Booking targetBooking = bookingRepository.findById(bookingId).orElseThrow();
        targetBooking.cancel();

        return BookingResponse.from(targetBooking);
    }

    @Transactional
    public BookingResponse reject(Long bookingId) {
        Booking targetBooking = bookingRepository.findById(bookingId).orElseThrow();
        targetBooking.reject();

        return BookingResponse.from(targetBooking);
    }

    private AmountResult getAmountResult(Booking booking) {
        LocalDate bookingCreatedDate = booking.getCreatedAt().toLocalDate();
        FeePolicy feePolicy = policyService.getFeePolicyByDate(bookingCreatedDate);
        DiscountPolicy discountPolicy = policyService.getDiscountPolicyByDate(bookingCreatedDate);
        return amountCalculationUtil.getAmountResult(booking, feePolicy, discountPolicy);
    }
}