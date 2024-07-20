package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "homestay_availability")
@IdClass(HomeStayAvailabilityId.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeStayAvailability {
    @Id
    @Column(name = "homestay_id")
    private Long homestayId;

    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Integer status;
}
