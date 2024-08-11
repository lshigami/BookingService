package com.example.demo.service;


import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    private static final int LONG_STAY=3;
    private static final double LONG_STAY_DISCOUNT_PERCENT=0.05;


    public double getDiscountAmount(double subtotal, int nights) {
        double discount = 0.0;

        if (nights >= LONG_STAY) {
            discount = subtotal * LONG_STAY_DISCOUNT_PERCENT;
        }

        return discount;
    }
}
