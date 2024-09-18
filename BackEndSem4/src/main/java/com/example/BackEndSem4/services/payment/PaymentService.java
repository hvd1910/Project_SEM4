package com.example.BackEndSem4.services.payment;

import com.example.BackEndSem4.configuration.VNPayConfig;
import com.example.BackEndSem4.dtos.PaymentDTO;
import com.example.BackEndSem4.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService{
    private final VNPayConfig vnPayConfig;

    @Override
    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L; //VNPAY 100L => 100000
        String bankCode = request.getParameter("bankCode");
        String bookingId = request.getParameter("bookingId");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(bookingId);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        queryUrl += "&vnp_SecureHash=" +  VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        String paymentUrl = vnPayConfig.getVnPayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .status("success")
                .message("Successfully created payment path.")
                .paymentUrl(paymentUrl).build();
    }
}
