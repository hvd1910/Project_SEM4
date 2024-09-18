package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByClinicNameAndAddress(String clinicName, String address);


    @Query("SELECT c FROM Clinic c " +
            "WHERE (:keyword IS NULL OR c.clinicName LIKE %:keyword%)")
    Page<Clinic> getClinicAll(@Param("keyword") String keyword,
                              Pageable pageable);
}
