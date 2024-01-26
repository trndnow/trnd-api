package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.OtpDetails;
import com.trnd.trndapi.security.playload.request.LoginRequest;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import com.trnd.trndapi.security.playload.request.TokenRefreshRequest;
import com.trnd.trndapi.security.service.AuthenticationService;
import com.trnd.trndapi.service.EmailServiceImpl;
import com.trnd.trndapi.service.OtpService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private static final String VALID_OTP_RESPONSE = "OTP is valid";
    private static final String INVALID_OTP_RESPONSE = "Invalid OTP";


    @PostMapping(value = "/requestOtp")
    public ResponseEntity<?> requestOtp(@RequestBody OtpDetails otpDetails) throws MessagingException {
        Map<String, Object> templateModel = new HashMap<>();
        String otp = otpService.requestOtp(otpDetails.getEmail());
        templateModel.put("otp", otp);    // Changed map key from "opt" to "otp"
        try {
            emailService.sendOtpEmail(
                    otpDetails.getEmail(),
                    "EMAIL OTP VERIFICATION",
                    templateModel);
            return ResponseEntity.ok(otp);
        } catch (MessagingException e) {
            // if you use SLF4J, you can do: log.error("Error sending OTP email", e);
            log.error("Error sending OTP email: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending OTP email");
        }
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValidOtp = otpService.validateOtp(email, otp);
        return isValidOtp ?
                ResponseEntity.ok(VALID_OTP_RESPONSE) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_OTP_RESPONSE);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult){
        return authenticationService.registerUser(signupRequest, bindingResult);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest, HttpServletRequest request){
        return authenticationService.refreshToken(tokenRefreshRequest, request);
    }


}
