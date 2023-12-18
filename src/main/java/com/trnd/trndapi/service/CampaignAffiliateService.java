package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignAffiliateDto;
import com.trnd.trndapi.entity.CampaignAffiliate;
import org.springframework.stereotype.Service;

@Service
public interface CampaignAffiliateService {
    CampaignAffiliateDto create(CampaignAffiliate campaignAffiliate);
}
