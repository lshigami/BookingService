package com.example.demo.dto.response;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingPriceResponse {
    private Double subtotal;
    private Double discount;
    private Double totalAmount;
    private String currency;
}
