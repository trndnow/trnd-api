package com.trnd.trndapi.merchant.events;

import com.trnd.trndapi.merchant.dto.MerchantDto;
import com.trnd.trndapi.merchant.service.MerchantService;
import com.trnd.trndapi.security.events.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MerchantEventHandler {
    private MerchantService merchantService;

    @EventListener
    @Async
    public void handleUserCreatedEvent(UserCreatedEvent event){
        log.info("User created event handler");
        log.info(event.getUserDto().toString());
        MerchantDto merchantDto = MerchantDto.builder()
                .merch_pri_contact_email(event.getUserDto().getEmail())
                .merch_pri_contact_phone(event.getUserDto().getMobile())
                .build();

        log.info("Creating Merchant Entry");
        merchantService.createMerchant(merchantDto);
    }
}
