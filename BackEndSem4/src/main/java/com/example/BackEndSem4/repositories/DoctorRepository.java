package com.example.BackEndSem4.repositories;

import com.example.BackEndSem4.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d " +
            "WHERE (:specialtyId IS NULL OR d.specialty.id = :specialtyId) " +
            "AND (:name IS NULL OR d.user.fullName LIKE %:name%)")
    Page<Doctor> findAllBySpecialtyIdAndName(@Param("specialtyId") Long specialtyId,
                                             @Param("name") String name,
                                              Pageable pageable);

    Optional<Doctor> findByUserId(Long UserId);
}
