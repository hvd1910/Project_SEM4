package com.example.BackEndSem4.exceptions;

import com.example.BackEndSem4.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // chỉ định lớp này  xử lý ngoại lệ chung
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> handleGeneralException(Exception exception) {
        return ResponseEntity.internalServerError().body(Response.builder()
                .status("error")
                .message("Please try again.")
                .data(null)
                .build());
    }

//    @ExceptionHandler(DataNotFoundException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Response> handleResourceNotFoundException(DataNotFoundException exception) {
//        return ResponseEntity.ok(Response.builder()
//                .status("error")
//                .message("Please try again.")
//                .data(null)
//                .build());
//    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("error", ex.getMessage(), null));
    }
}
