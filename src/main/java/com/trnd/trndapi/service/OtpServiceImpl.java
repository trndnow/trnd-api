package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.OtpDetails;
import com.trnd.trndapi.entity.OtpEntity;
import com.trnd.trndapi.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService{

    private final Map<String, OtpDetails> otpData = new HashMap<>();
    private final OtpRepository otpRepository;

    /**
     * @param email
     * @return
     */
    @Override
    public String requestOtp(String email) {
        String otp = generateOTP(6);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);
        otpData.put(email, OtpDetails.builder().email(email).otp(otp).expirationTime(expirationTime).build());
        storeOtp(email,otp,expirationTime);
        return otp;
    }

    /**
     * @param otpLength
     * @return String otp
     */
    public String generateOTP(int otpLength) {
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

    /**
     * @param email
     * @param otp
     * @return boolean
     */
    @Override
    public boolean validateOtp(String email, String otp) {
        OtpEntity otpEntity = otpRepository.findById(email).orElse(null);
        if (otpEntity != null && !otpEntity.isExpired() && otpEntity.getOtp().equals(otp)) {
            otpData.remove(email); // Remove OTP after successful validation
            return true;
        }
        return false;
    }

    /**
     * @param email
     * @param otp
     * @param expirationTime
     * @return void
     */
    @Override
    public void storeOtp(String email, String otp, LocalDateTime expirationTime) {
        otpRepository.save(OtpEntity.builder()
                .email(email)
                .otp(otp)
                .expirationTime(expirationTime).build());
    }

    /**
     * @param email
     * @return
     */
    @Override
    public String getOtp(String email) {
        return otpRepository.findById(email).map(OtpEntity::getOtp).orElse(null);
    }

    /**
     * @param email
     */
    @Override
    public void clearOtp(String email) {
        otpRepository.deleteById(email);
    }
}
