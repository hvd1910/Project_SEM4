package com.example.BackEndSem4.controllers;

import com.example.BackEndSem4.dtos.SpecialtyDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Specialty;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.response.specialty.SpecialtyListResponse;
import com.example.BackEndSem4.services.specialty.SpecialtyService;
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
@RequestMapping("${api.prefix}/spcialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    // GET all specialties
    @GetMapping("")
    public ResponseEntity<?> getAllSpecialties( @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "999999999") int limit,
                                                @RequestParam(defaultValue = "") String sort,
                                                @RequestParam(defaultValue = "") String keyword) {
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by(sortDirection,"id")
        );

        Page<Specialty> specialties = specialtyService.getSpecialtyAll(keyword, pageRequest);

        int totalPages = specialties.getTotalPages();
        List<Specialty> specialtyList = specialties.getContent();

        return ResponseEntity.ok(new Response("success", "Get specialties successfully.",
                SpecialtyListResponse.builder()
                        .specialtyList(specialtyList)
                        .totalPages(totalPages)
                        .build()));
    }

    // GET specialty by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getSpecialtyById(@PathVariable Long id) throws DataNotFoundException {
            Specialty specialty = specialtyService.getSpecialtyById(id);
            return ResponseEntity.ok(new Response("success", "Get specialty successfully.", specialty));
    }

    // POST create new specialty
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createSpecialty(@Valid @RequestBody SpecialtyDTO specialtyDTO,
                                             BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new Response("error", "Validation failed", errorMessages));
        }
            Specialty specialty = specialtyService.createSpecialty(specialtyDTO);
        return ResponseEntity.ok(new Response("success", "Create specialty successfully.", specialty));

    }

    // PUT update specialty
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSpecialty(@PathVariable Long id,@Valid @RequestBody SpecialtyDTO specialtyDTO,
                                             BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new Response("error", "Validation failed", errorMessages));
        }
            Specialty updatedSpecialty = specialtyService.updateSpecialty(id, specialtyDTO);
        return ResponseEntity.ok(new Response("success", "Update specialty successfully.", null));

    }

    // DELETE specialty by id
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpecialtyById(@PathVariable Long id) throws Exception {
            specialtyService.deleteSpecialtyById(id);
        return ResponseEntity.ok(new Response("success", "Delete specialty by id: " + id + " successfully.", null));
    }
}
