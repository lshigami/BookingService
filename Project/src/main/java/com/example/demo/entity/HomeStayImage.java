package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "home_stay_images")
@Getter
@Setter
public class HomeStayImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @ManyToOne
//    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "home_stay_id")
    private HomeStay homeStay;
}