package com.example.demo.controller;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.APIResponse;
import com.example.demo.service.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public APIResponse createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        log.info("Request to create booking: {}", bookingRequest);
        System.out.println(bookingRequest.getCheckOutDate());
        System.out.println(bookingRequest.getCheckInDate());
        System.out.println(bookingRequest.getNote());
        return APIResponse.builder().data(bookingService.bookHomestay(bookingRequest)).build();
    }
    @GetMapping
    public APIResponse createBooking(
            @RequestParam  String requestID,
            @RequestParam Long userID,
            @RequestParam Long homestayID,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam int guests,
            @RequestParam String note) {

        BookingRequest bookingRequest = BookingRequest.builder()
                .requestID(requestID)
                .userID(userID)
                .homestayID(homestayID)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .guests(guests)
                .note(note)
                .build();

        log.info("Request to create booking: {}", bookingRequest);
        return APIResponse.builder().data(bookingService.bookHomestay(bookingRequest)).build();
    }
}
