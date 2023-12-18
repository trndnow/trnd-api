package com.trnd.trndapi.events;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.service.EmailServiceImpl;
import com.trnd.trndapi.service.MerchantService;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.mapper.UserMapper;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MerchantEventListener {
    private MerchantService merchantService;
    @Autowired
    private EmailServiceImpl emailService;
    public MerchantEventListener(MerchantService merchantService) {
        this.merchantService = merchantService;
    }



    @EventListener
    @Async
    public void handleMerchantProfileCreatedEvent(MerchantCreatedEvent event){
        log.info("Merchant Profile Created Event");
    /** TODO:
        Send Email notification that users' profile is created and onboarding approval from trndnow is pending.
        will be notified as the review & approval process is completed.
     **/
        Map<String, Object> templateModel = new HashMap<>();

        try {
            emailService.sendOnboardingEmail(
                    event.getMerchantDto().getMerchPriContactEmail(),
                    "Welcome to trnd",
                    templateModel);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


}
