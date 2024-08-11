package com.example.demo.service;

import com.example.demo.dto.response.BookingPriceResponse;
import com.example.demo.entity.HomeStayAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {
    @Autowired
    private DiscountService discountService;

    public BookingPriceResponse calculatePrice(List<HomeStayAvailability> homeStayAvailabilities) {
        int nights=homeStayAvailabilities.size();
        double subtotal = 0.0;
        for(HomeStayAvailability homeStayAvailability: homeStayAvailabilities){
            subtotal += homeStayAvailability.getPrice();
        }
        double discount = discountService.getDiscountAmount(subtotal,nights);
        double total = subtotal - discount;
        return BookingPriceResponse.builder()
                .subtotal(subtotal)
                .discount(discount)
                .totalAmount(total)
                .currency("USD")
                .build();
    }
}
