package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignAffiliateDto;
import com.trnd.trndapi.entity.CampaignAffiliate;
import com.trnd.trndapi.mapper.CampaignAffiliateMapper;
import com.trnd.trndapi.repository.CampaignAffiliateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampaignAffiliateServiceImpl implements CampaignAffiliateService{

    private final CampaignAffiliateRepository campaignAffiliateRepository;
    private final CampaignAffiliateMapper campaignAffiliateMapper;

    /**
     * @param campaignAffiliate
     * @return
     */
    @Override
    public CampaignAffiliateDto create(CampaignAffiliate campaignAffiliate) {
        CampaignAffiliate saveCampAffiliate = campaignAffiliateRepository.save(campaignAffiliate);
        return campaignAffiliateMapper.toDto(saveCampAffiliate);
    }
}
