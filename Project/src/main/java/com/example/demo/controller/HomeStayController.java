package com.example.demo.controller;

import com.example.demo.dto.response.APIResponse;
import com.example.demo.dto.response.HomeStaySearchResponse;
import com.example.demo.entity.HomeStay;
import com.example.demo.service.searching.HomeStayService;
import com.example.demo.untils.DateUltil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homestays")
public class HomeStayController {

    @Autowired
    private HomeStayService homeStayService;

    @GetMapping
    public APIResponse searchHomeStay(@RequestParam Double longitude,
                                      @RequestParam Double latitude,
                                      @RequestParam Integer guests,
                                      @RequestParam String checkinDate,
                                      @RequestParam String checkoutDate,
                                      @RequestParam Double radius,
                                      @RequestParam Integer nights) {
        List<HomeStaySearchResponse> homeStays = homeStayService.searchHomeStay(longitude, latitude, guests, DateUltil.parse(checkinDate), DateUltil.parse(checkoutDate), radius, nights);
        return APIResponse.builder().code(1000).data(homeStays).build();
    }

    @GetMapping("/{id}")
    public APIResponse getHomeStay(@PathVariable Long id) {
        HomeStay homeStay = homeStayService.getHomeStayById(id);
        return APIResponse.builder().code(1000).data(homeStay).build();
    }
}
