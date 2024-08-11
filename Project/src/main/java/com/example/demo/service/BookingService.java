package com.example.demo.service;

import com.example.demo.converter.BookingConverter;
import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.entity.Booking;
import com.example.demo.entity.HomeStay;
import com.example.demo.entity.HomeStayAvailability;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.searching.HomeStayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class BookingService {

    BookingRepository bookingRepository;
    HomeStayService homeStayService;
    HomeStayAvailabilityService homeStayAvailabilityService;
    PricingService pricingService;
    BookingConverter bookingConverter;


    @Transactional
    public BookingResponse bookHomestay( BookingRequest bookingRequest) {
        validateRequest(bookingRequest);
        validateHomeStay(bookingRequest);
        List<HomeStayAvailability> homeStayAvailabilityList = homeStayAvailabilityService.findHomestayAvailableForBoking(bookingRequest.getHomestayID(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
        var BookingPriceResponse = pricingService.calculatePrice(homeStayAvailabilityList);
        Booking booking = Booking.builder()
                .checkinDate(bookingRequest.getCheckInDate())
                .checkoutDate(bookingRequest.getCheckOutDate())
                .guests(bookingRequest.getGuests())
                .homestayId(bookingRequest.getHomestayID())
                .subtotal(BookingPriceResponse.getSubtotal())
                .discount(BookingPriceResponse.getDiscount())
                .total(BookingPriceResponse.getTotalAmount())
                .currency(BookingPriceResponse.getCurrency())
                .userId(bookingRequest.getUserID())
                .status(1)
                .requestId(bookingRequest.getRequestID())
                .note(bookingRequest.getNote())
                .build();
        for (HomeStayAvailability homeStayAvailability : homeStayAvailabilityList) {
            homeStayAvailability.setStatus(0);
        }
        homeStayAvailabilityService.saveAll(homeStayAvailabilityList);
        bookingRepository.save(booking);

        return bookingConverter.convertToResponse(booking);
    }

    private void validateRequest(BookingRequest request) {
        log.info("Validating booking request: {}", request);
        LocalDate checkinDate = request.getCheckInDate();
        LocalDate checkoutDate = request.getCheckOutDate();
        LocalDate currentDate = LocalDate.now();

        if (checkinDate.isBefore(currentDate) || checkinDate.isAfter(checkoutDate)) {
            throw new AppException(ErrorCode.INVALID_CHECK_IN_DATE);
        }

        if (request.getGuests() <= 0) {
            throw new AppException(ErrorCode.INVALID_GUEST_NUMBER);
        }
    }

    private void validateHomeStay(BookingRequest request) {
        HomeStay homeStay = homeStayService.getHomeStayById(request.getHomestayID());
        if (homeStay == null) {
            throw new AppException(ErrorCode.HOME_STAY_NOT_FOUND);
        }
        if (homeStay.getGuests() < request.getGuests()) {
            throw new AppException(ErrorCode.INVALID_GUEST_NUMBER);
        }
        if (homeStay.getStatus() != 1) {
            throw new AppException(ErrorCode.HOMESTAY_NOT_AVAILABLE);
        }
        log.info("Homestay is valid: {}", homeStay);
    }
}
