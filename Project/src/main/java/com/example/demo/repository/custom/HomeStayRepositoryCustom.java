package com.example.demo.repository.custom;

import com.example.demo.dto.response.HomeStaySearchResponse;

import java.time.LocalDate;
import java.util.List;

public interface HomeStayRepositoryCustom {
    List<HomeStaySearchResponse> search(Double longitude, Double latitude, Integer guests, LocalDate checkinDate, LocalDate checkoutDate, Double radius, Integer nights);
}
