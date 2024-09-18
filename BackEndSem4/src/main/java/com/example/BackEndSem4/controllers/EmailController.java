package com.example.BackEndSem4.controllers;

import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.Email;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.services.email.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;


    @PreAuthorize("hasRole('ROLE_ADMIN')  or hasRole('ROLE_DOCTOR')")
    @PostMapping("/medicalResult")
    public ResponseEntity<?> sendEmailMedicalResult(@Valid @RequestBody Email email) throws DataNotFoundException {
        emailService.sendEmailMedicalResult(email);
        return ResponseEntity.ok(new Response("success", "Sent email successfully.", null));

    }

}

