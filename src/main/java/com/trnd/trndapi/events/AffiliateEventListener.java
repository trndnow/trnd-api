package com.trnd.trndapi.events;

import com.trnd.trndapi.service.AffiliateService;
import com.trnd.trndapi.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class AffiliateEventListener {
    private final AffiliateService affiliateService;
    private final EmailService emailService;

    @EventListener
    @Async
    public void handleAffiliateProfileCreatedEvent(AffiliateCreatedEvent event){
        log.info("Affiliate Profile Created Event");
        /** TODO:
         Send Email notification that users' profile is created and onboarding approval from trndnow is pending.
         will be notified as the review & approval process is completed.
         **/
        Map<String, Object> onboardingEmailModel = new HashMap<>();

        Map<String, Object> pendingApprovalEmailModel = new HashMap<>();
        pendingApprovalEmailModel.put("affiliate_name", event.getAffiliateDto().getAffContactEmail());
        try {
            emailService.sendOnboardingEmail(
                    event.getAffiliateDto().getAffContactEmail(),
                    "Welcome to trnd",
                    onboardingEmailModel);

            emailService.sendPendingApprovalEmail(
                event.getMerchantDto().getMerchPriContactEmail(),
                    "Affiliate Approval Pending",
                    pendingApprovalEmailModel
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
