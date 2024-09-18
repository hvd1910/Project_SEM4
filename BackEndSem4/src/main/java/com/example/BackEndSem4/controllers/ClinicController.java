package com.example.BackEndSem4.controllers;

import com.example.BackEndSem4.dtos.ClinicDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Clinic;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.response.clinic.ClinicListResponse;
import com.example.BackEndSem4.services.clinic.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping("")
    public ResponseEntity<?> getAllClinics( @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "999999999") int limit,
                                            @RequestParam(defaultValue = "") String sort,
                                            @RequestParam(defaultValue = "") String keyword) {
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by(sortDirection,"id")
        );

        Page<Clinic> clinics = clinicService.getClinicAll(keyword, pageRequest);

        int totalPages = clinics.getTotalPages();
        List<Clinic> clinicList = clinics.getContent();
        return ResponseEntity.ok(new Response("success", "Get clinics successfully.",
                ClinicListResponse.builder()
                        .clinicList(clinicList)
                        .totalPages(totalPages)
                        .build()));
    }

    // GET clinic by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getClinicById(@PathVariable Long id) throws DataNotFoundException {
        Clinic clinic = clinicService.getClinicById(id);
        return ResponseEntity.ok(new Response("success", "Get clinic successfully.", clinic));
    }

    // POST create new clinic
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createClinic(@Valid @RequestBody ClinicDTO clinicDTO,
                                             BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new Response("error", "Validation failed", errorMessages));
        }
        Clinic clinic = clinicService.createClinic(clinicDTO);
        return ResponseEntity.ok(new Response("success", "Create clinic successfully.", clinic));

    }

    // PUT update clinic
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClinic(@PathVariable Long id,@Valid @RequestBody ClinicDTO clinicDTO,
                                             BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new Response("error", "Validation failed", errorMessages));
        }
        Clinic updatedClinic = clinicService.updateClinic(id, clinicDTO);
        return ResponseEntity.ok(new Response("success", "Update clinic successfully.", null));

    }

    // DELETE clinic by id
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClinicById(@PathVariable Long id) throws Exception {
        clinicService.deleteClinicById(id);
        return ResponseEntity.ok(new Response("success", "Delete clinic by id: " + id + " successfully.", null));
    }
}
