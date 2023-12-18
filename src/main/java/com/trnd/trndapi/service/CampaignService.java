package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.dto.MerchantDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CampaignService {
    CampaignDto addCampaign(CampaignDto campaignDto);

    CampaignDto viewCampaign(long campaignId);

    List<CampaignDto> viewAllCampaign();
    CampaignDto defaultCampaignAssociation(MerchantDto merchantDto);

    CampaignDto getCampaignByMerchantCode(String merchantCode);
}
