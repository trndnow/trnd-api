package com.trnd.trndapi.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface OtpService {

     String requestOtp(String email);
    boolean validateOtp(String email, String otp);
    void storeOtp(String email, String otp, LocalDateTime expirationTime);
    String getOtp(String email);
    void clearOtp(String email);
}
