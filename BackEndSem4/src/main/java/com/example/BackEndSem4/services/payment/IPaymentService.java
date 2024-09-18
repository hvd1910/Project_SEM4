package com.example.BackEndSem4.services.payment;


import com.example.BackEndSem4.dtos.PaymentDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request);
}

