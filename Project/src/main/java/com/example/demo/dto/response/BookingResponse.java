package com.example.demo.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Long userId;
    private Long homestayId;
    private String checkinDate;
    private String checkoutDate;
    private Integer guests;

    private Integer status;

    private Double subtotal;
    private Double discount;
    private Double total;

    private String currency;

    private String requestId;
    private String note;
}