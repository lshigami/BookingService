package com.example.demo.service.searching;

import com.example.demo.dto.response.HomeStaySearchResponse;
import com.example.demo.entity.HomeStay;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.HomeStayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class HomeStayService {

    @Autowired
    private HomeStayRepository homeStayRepository;

    public List<HomeStaySearchResponse> searchHomeStay(Double longitude, Double latitude, Integer guests, LocalDate checkinDate, LocalDate checkoutDate, Double radius, Integer nights) {
        return homeStayRepository.search(longitude, latitude, guests, checkinDate, checkoutDate, radius, nights);
    }

    public HomeStay getHomeStayById(Long id) {
        var x= homeStayRepository.findById(id).orElse(null);
        return x;
    }
}
