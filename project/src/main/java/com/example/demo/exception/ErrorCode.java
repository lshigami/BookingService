package com.example.demo.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found",HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1002, "User already exists",HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(1003, "Invalid request",HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(1004, "Internal server error",HttpStatus.INTERNAL_SERVER_ERROR),
    PASSWORD_MUST_AT_LEAST_6_CHARACTERS(1005, "Password must be at least 6 characters",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You must login to access",HttpStatus.FORBIDDEN),
    INVALID_DATE_OF_BIRTH(1008, "Invalid date of birth",HttpStatus.BAD_REQUEST),
    BOOKING_CANT_BE_OVER_30_DAYS(1009, "Booking can't be over 30 days",HttpStatus.BAD_REQUEST),
    HOMESTAY_NOT_AVAILABLE(1010, "Homestay not available",HttpStatus.BAD_REQUEST),
    INVALID_CHECK_IN_DATE(1011, "Invalid check in date",HttpStatus.BAD_REQUEST),
    INVALID_GUEST_NUMBER(1012, "Invalid guest number",HttpStatus.BAD_REQUEST),
    HOME_STAY_NOT_FOUND(1013, "Homestay not found",HttpStatus.NOT_FOUND),
    UNCATEGORIZED(9999, "You dont have permission", HttpStatus.INTERNAL_SERVER_ERROR);
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
