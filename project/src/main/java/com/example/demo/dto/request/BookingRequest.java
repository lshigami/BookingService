package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    @NotBlank(message = "Request ID is required")
    private String requestID;
    @NotNull(message = "User ID is required")
    private Long userID;
    @NotNull(message = "Homestay ID is required")
    private Long homestayID;
    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;
    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;
    @Positive(message = "Number of guests must be positive")
    private int guests;
    @Length(max = 255, message = "Note must not exceed 255 characters")
    private String note;

}
