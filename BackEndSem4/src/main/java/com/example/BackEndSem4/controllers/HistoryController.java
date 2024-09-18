package com.example.BackEndSem4.controllers;

import com.example.BackEndSem4.dtos.HistoryDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.History;
import com.example.BackEndSem4.response.Response;
import com.example.BackEndSem4.response.history.HistoryResponse;
import com.example.BackEndSem4.services.history.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/histories")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_DOCTOR')")
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getHistoryByBookingId(@PathVariable Long bookingId) throws DataNotFoundException {
        History history = historyService.getHistoryByBookingId(bookingId);
        return ResponseEntity.ok(new Response("success", "Get history successfully.", HistoryResponse.fromHistoryResponse(history)));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')  or hasRole('ROLE_DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHistory(@PathVariable Long id, @RequestBody HistoryDTO historyDTO) {
        History updatedHistory = historyService.updateHistory(id, historyDTO);
        return ResponseEntity.ok(new Response("success", "Update history successfully.", null));

    }

}
