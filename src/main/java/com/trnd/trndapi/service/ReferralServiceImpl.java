package com.trnd.trndapi.service;

import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReferralServiceImpl implements ReferralService{
    private final UserRepository userRepository;
    private final String baseUrl = "http://localhost:9999/referral";
    @Override
    public String createReferralLink(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        if (user.getReferralCode() == null) {
//            String referralCode = UUID.randomUUID().toString();
//            user.setReferralCode(referralCode);
//            userRepository.save(user);
//        }
//
//        // The actual discount logic can be handled when the link is accessed, not when it's generated
//        return baseUrl + "?ref=" + user.getReferralCode();
        return null;
    }
}
