package com.example.BackEndSem4.controllers;


import com.example.BackEndSem4.dtos.DoctorDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Doctor;
import com.example.BackEndSem4.repositories.DoctorRepository;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.response.doctor.DoctorListResponse;
import com.example.BackEndSem4.response.doctor.DoctorResponse;
import com.example.BackEndSem4.services.doctor.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("")
    public ResponseEntity<?> getDoctorsBySpecialtyIdAndName(@RequestParam(defaultValue = "") Long specialtyId,
                                                         @RequestParam(defaultValue = "") String name,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "999999999") int limit,
                                                         @RequestParam(defaultValue = "") String sort) {
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by(sortDirection,"id")
        );

        Page<DoctorResponse>doctors =doctorService.getDoctorsBySpecialtyIdAndName(specialtyId, name, pageRequest)
                                        .map(DoctorResponse::fromDoctorResponse);

        int totalPages = doctors.getTotalPages();
        List<DoctorResponse> doctorResponseList = doctors.getContent();

        return ResponseEntity.ok(new Response("success", "Get doctors successfully.",
                DoctorListResponse.builder()
                        .doctors(doctorResponseList)
                        .totalPages(totalPages)
                        .build()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) throws DataNotFoundException {
        Doctor doctor =  doctorService.getDoctorById(id);
        return ResponseEntity.ok(new Response("success", "Get doctor successfully.", DoctorResponse.fromDoctorResponse(doctor)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO,
                                               BindingResult result) throws DataNotFoundException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new Response("error", "Validation failed", errorMessages));
        }
        Doctor doctor = doctorService.createDoctor(doctorDTO);
        return ResponseEntity.ok(new Response("success", "Get doctor successfully.", DoctorResponse.fromDoctorResponse(doctor)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctorById( @PathVariable Long id, @Valid @RequestBody DoctorDTO doctorDTO) throws DataNotFoundException {
        Doctor doctorUpdate = doctorService.updateDoctor(id, doctorDTO);
        return ResponseEntity.ok(new Response("success", "Update doctor successfully.", null));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable Long id) throws Exception {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.ok(new Response("success", "Delete doctor successfully.", null));
    }

}
