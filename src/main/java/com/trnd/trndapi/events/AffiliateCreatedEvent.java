package com.trnd.trndapi.events;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.dto.MerchantDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class AffiliateCreatedEvent extends ApplicationEvent {
    private AffiliateDto affiliateDto;
    private MerchantDto merchantDto;

    public AffiliateCreatedEvent(AffiliateDto affiliateDto, MerchantDto merchantDto) {
        super(affiliateDto);
        this.affiliateDto = affiliateDto;
        this.merchantDto = merchantDto;

    }

    public AffiliateCreatedEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public AffiliateCreatedEvent(AffiliateDto affiliateDto){
        super(affiliateDto);
        this.affiliateDto = affiliateDto;
    }
}
