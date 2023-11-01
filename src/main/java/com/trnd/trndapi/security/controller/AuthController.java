package com.trnd.trndapi.security.controller;

import com.trnd.trndapi.commons.service.EmailService;
import com.trnd.trndapi.security.playload.request.LoginRequest;
import com.trnd.trndapi.security.playload.request.SignupRequest;
import com.trnd.trndapi.security.playload.request.TokenRefreshRequest;
import com.trnd.trndapi.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @Autowired
    private EmailService emailService;


    @PostMapping("/email-verification")
    public ResponseEntity<?> emailVerification(@RequestBody String email){
        String otp = generateOTP(6);
        emailService.sendSimpleMessage(
                "hello@trndnow.com",
                "EMAIL OTP VERIFICATION",
                "Email OTP "+otp
        );
        return ResponseEntity.ok(otp);
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

    public static String generateOTP(int otpLength) {
        // A strong OTP should typically have alphanumeric characters
        // but for simplicity, we're only using numeric digits
        String numbers = "1234567890";
        Random random = new Random();

        // Using StringBuilder to efficiently concatenate strings
        StringBuilder otp = new StringBuilder(otpLength);

        for (int i = 0; i < otpLength; i++) {
            // generate a random number between 0 to numbers.length()
            int index = random.nextInt(numbers.length());
            // get character specified by index from the string
            otp.append(numbers.charAt(index));
        }

        return otp.toString();
    }


}
