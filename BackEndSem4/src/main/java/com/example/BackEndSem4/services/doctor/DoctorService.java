package com.example.BackEndSem4.services.doctor;

import com.example.BackEndSem4.dtos.DoctorDTO;
import com.example.BackEndSem4.dtos.SpecialtyDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Doctor;
import com.example.BackEndSem4.models.Specialty;
import com.example.BackEndSem4.models.User;
import com.example.BackEndSem4.repositories.DoctorRepository;
import com.example.BackEndSem4.repositories.SpecialtyRepository;
import com.example.BackEndSem4.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService{
    private  final DoctorRepository doctorRepository;
    private  final SpecialtyRepository specialtyRepository;
    private final UserRepository userRepository;
    @Override
    public Page<Doctor> getDoctorsBySpecialtyIdAndName(Long specialtyId, String name, Pageable pageable) {
        return doctorRepository.findAllBySpecialtyIdAndName(specialtyId, name, pageable);
    }

    @Override
    public Doctor getDoctorById(Long id) throws DataNotFoundException {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Doctor not found with id " + id));
    }

    @Override
    public Doctor createDoctor(DoctorDTO doctorDTO) throws DataNotFoundException {
        User userExsiting = userRepository.findById(doctorDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found with id " + doctorDTO.getUserId()));

        Specialty specialtyExisting = specialtyRepository.findById(doctorDTO.getSpecialtyId())
                .orElseThrow(() -> new DataNotFoundException("Specialty not found with id " + doctorDTO.getSpecialtyId()));

        Optional<Doctor> doctorExisting = doctorRepository.findByUserId(doctorDTO.getUserId());
        if (doctorExisting.isPresent()) {
            throw new DataNotFoundException("Doctor already exists.");
        }


        Doctor doctor = Doctor.builder()
                .user(userExsiting)
                .specialty(specialtyExisting)
                .avatar(doctorDTO.getAvatar())
                .bio(doctorDTO.getBio())
                .experience(doctorDTO.getExperience())
                .qualification(doctorDTO.getQualification())
                .active(true)
                .build();
        doctor.setCreatedAt(LocalDateTime.now());
        doctor.setUpdatedAt(LocalDateTime.now());
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, DoctorDTO doctorDTO) throws DataNotFoundException {
        Doctor doctorExisting = doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Doctor not found with id " + id));

        User userExsiting = userRepository.findById(doctorDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found with id " + doctorDTO.getUserId()));

        Specialty specialtyExisting = specialtyRepository.findById(doctorDTO.getSpecialtyId())
                .orElseThrow(() -> new DataNotFoundException("Specialty not found with id " + doctorDTO.getSpecialtyId()));

        doctorExisting.setUser(userExsiting);
        doctorExisting.setSpecialty(specialtyExisting);
        doctorExisting.setAvatar(doctorDTO.getAvatar());
        doctorExisting.setBio(doctorDTO.getBio());
        doctorExisting.setExperience(doctorDTO.getExperience());
        doctorExisting.setQualification(doctorDTO.getQualification());
        doctorExisting.setActive(doctorDTO.isActive());
        doctorExisting.setUpdatedAt(LocalDateTime.now());
        return doctorRepository.save(doctorExisting);
    }

    @Override
    public void deleteDoctorById(Long id) throws Exception {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Doctor not found with id " + id));
        doctorRepository.delete(doctor);
    }
}
