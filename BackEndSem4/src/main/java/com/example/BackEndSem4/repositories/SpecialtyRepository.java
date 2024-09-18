package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Optional<Specialty> findBySpecialtyName(String specialtyName);

    @Query("SELECT s FROM Specialty s " +
            "WHERE (:keyword IS NULL OR s.specialtyName LIKE %:keyword%)")
    Page<Specialty> getSpecialtyAll(@Param("keyword") String keyword,
                              Pageable pageable);

}
