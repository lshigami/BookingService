package com.example.demo.repository;

import com.example.demo.entity.HomeStay;
import com.example.demo.repository.custom.HomeStayRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeStayRepository extends JpaRepository<HomeStay, Long>, HomeStayRepositoryCustom {
}
