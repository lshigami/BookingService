package com.example.demo.converter;

import com.example.demo.dto.response.BookingResponse;
import com.example.demo.entity.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    @Autowired
    private ModelMapper modelMapper;

    public BookingResponse convertToResponse(Booking booking) {
        return modelMapper.map(booking, BookingResponse.class);
    }

}
