package com.example.demo.service;

import com.example.demo.entity.HomeStayAvailability;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.HomeStayAvailabilityRepository;
import com.example.demo.untils.DateUltil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class HomeStayAvailabilityService {
    private static final int MAX_DAYS = 30;

    HomeStayAvailabilityRepository homeStayAvailabilityRepository;

    public List<HomeStayAvailability>findHomestayAvailableForBoking(Long homestayID, LocalDate checkInDate, LocalDate checkOutDate){
        Long nights = DateUltil.getDiffInDays(checkInDate, checkOutDate);
        if(nights > MAX_DAYS){
            throw new AppException(ErrorCode.BOOKING_CANT_BE_OVER_30_DAYS);
        }
        List<HomeStayAvailability>homeStayAvailabilities = homeStayAvailabilityRepository.findAndLockHomestayAvailability(homestayID, 1, checkInDate, checkOutDate);
        if(homeStayAvailabilities.size()<nights || homeStayAvailabilities.isEmpty()){
            throw new AppException(ErrorCode.HOMESTAY_NOT_AVAILABLE);
        }
        return homeStayAvailabilities;
    }

//    @Transactional
    public void saveAll(List<HomeStayAvailability>homeStayAvailabilities){
        homeStayAvailabilityRepository.saveAll(homeStayAvailabilities);
    }
}
