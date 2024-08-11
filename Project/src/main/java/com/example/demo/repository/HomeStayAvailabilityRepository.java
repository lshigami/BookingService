package com.example.demo.repository;

import com.example.demo.entity.HomeStayAvailability;
//import com.example.demo.entity.HomeStayAvailabilityId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HomeStayAvailabilityRepository extends JpaRepository<HomeStayAvailability, Long> {

    @Query(value = """
        SELECT ha
        FROM HomeStayAvailability ha
        WHERE ha.homestayId = :homestayId
        AND ha.status = :status
        AND ha.date BETWEEN :checkinDate AND :checkoutDate
        """)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<HomeStayAvailability> findAndLockHomestayAvailability(@Param("homestayId") Long homestayId,
                                                               @Param("status") Integer status,
                                                               @Param("checkinDate") LocalDate checkinDate,
                                                               @Param("checkoutDate") LocalDate checkoutDate);
}
