package com.example.demo.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeStayAvailabilityId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long homestayId;
    private LocalDate date;
}
