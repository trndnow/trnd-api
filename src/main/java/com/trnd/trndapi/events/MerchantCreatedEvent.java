package com.trnd.trndapi.events;

import com.trnd.trndapi.dto.MerchantDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MerchantCreatedEvent extends ApplicationEvent {
    private MerchantDto merchantDto;

    public MerchantCreatedEvent(MerchantDto merchantDto) {
        super(merchantDto);
        this.merchantDto = merchantDto;
    }

}
