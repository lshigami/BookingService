package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HomeStaySearchResponse {
    private Long id;
    private String description;
    private Double longitude;
    private Double latitude;
    private Double nightAmount;
    private Double totalAmount;
    private List<String> images;  // Thêm danh sách ảnh
}
