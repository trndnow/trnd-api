package com.trnd.trndapi.merchant.events;

import com.trnd.trndapi.merchant.dto.MerchantDto;
import com.trnd.trndapi.merchant.service.MerchantService;
import com.trnd.trndapi.merchant.utils.MerchantUtils;
import com.trnd.trndapi.security.dto.UserDto;
import com.trnd.trndapi.security.entity.Role;
import com.trnd.trndapi.security.enums.ERole;
import com.trnd.trndapi.security.events.UserCreatedEvent;
import com.trnd.trndapi.security.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class MerchantEventListener {
    private MerchantService merchantService;
    public MerchantEventListener(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @EventListener
    @Async
    public void handleUserCreatedEvent(UserCreatedEvent event){
        log.info("User created event handler");
        log.info(event.getUser().toString());
        String[] rolesArray = event.getUser().getRoles().stream()
                .map(r -> r.getName().name()) // Replace with the appropriate method to get the role's name
                .toArray(String[]::new);
        if(Arrays.stream(rolesArray).anyMatch(r->r.contains(ERole.ROLE_MERCHANT.name()))){
            //TODO: Move the unique_link_generation to a seperate service
            MerchantDto merchantDto = MerchantDto.builder()
                    .merch_unique_code(event.getUser().getUniqueId().toString())
                    .merch_unique_link(generateUniqueLink(event.getUser().getUniqueId().toString()))
                    .merch_pri_contact_email(event.getUser().getEmail())
                    .merch_pri_contact_phone(event.getUser().getMobile())
                    .user(UserMapper.INSTANCE.toDto(event.getUser()))
                    .build();
            log.info("Creating Merchant Entry");
            merchantService.createMerchant(merchantDto);
        }else {
            log.info("LOGGING FROM: {}", MerchantEventListener.class.getName());
            log.info("ROLE IS NOT MERCHANT ");
        }
    }

    private String generateUniqueLink(String uniqueCode) {
        String baseUrl = "http://localhost:9999/referral";
        return baseUrl + "?ref=" + uniqueCode;
    }
}
