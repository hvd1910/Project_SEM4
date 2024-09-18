package com.example.BackEndSem4.configuration;

import com.example.BackEndSem4.utils.VNPayUtil;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
@Getter
public class VNPayConfig {

    private String vnPayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    private String vnp_ReturnUrl = "http://localhost:3000/checkout/payment/";

    private String vnp_TmnCode = "GQPB22K0";

    private String secretKey = "J5FAR48WWYJUT3A6O84CLHVWJE1F638A";

    private String vnp_Version = "2.1.0";

    private String vnp_Command = "pay";

    private String orderType = "other";


    public Map<String, String> getVNPayConfig(String bookingId) {

        final String number_transaction = VNPayUtil.getRandomNumber(8);


        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", number_transaction);
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang: " +  bookingId);
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl + bookingId  );
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }

}

