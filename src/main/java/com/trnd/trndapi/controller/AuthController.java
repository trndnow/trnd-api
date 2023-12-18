package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.OtpDetails;
import com.trnd.trndapi.service.EmailServiceImpl;
import com.trnd.trndapi.security.playload.request.LoginRequest;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import com.trnd.trndapi.security.playload.request.TokenRefreshRequest;
import com.trnd.trndapi.security.service.AuthenticationService;
import com.trnd.trndapi.service.OtpService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final OtpService otpService;
    private final EmailServiceImpl emailService;



    @PostMapping(value = "/requestOtp")
    public ResponseEntity<?> requestOtp(@RequestBody OtpDetails otpDetails) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        String otp = otpService.requestOtp(otpDetails.getEmail());
        templateModel.put("opt", otp);
        emailService.sendOtpEmail(
                otpDetails.getEmail(),
                "EMAIL OTP VERIFICATION",
                templateModel);
        return ResponseEntity.ok(otp);
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestParam String email, @RequestParam String otp) {
        if (otpService.validateOtp(email, otp)) {
            return ResponseEntity.ok("OTP is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        return authenticationService.registerUser(signupRequest);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest, HttpServletRequest request){
        return authenticationService.refreshToken(tokenRefreshRequest, request);
    }


}
