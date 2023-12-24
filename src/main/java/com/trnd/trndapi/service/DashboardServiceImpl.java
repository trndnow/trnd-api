package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.DashboardStatistics;
import com.trnd.trndapi.repository.AffiliateRepository;
import com.trnd.trndapi.repository.CampaignRepository;
import com.trnd.trndapi.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    private final MerchantRepository merchantRepository;
    private final AffiliateRepository affiliateRepository;
    private final CampaignRepository campaignRepository;


    /**
     * @return DashboardStatistics
     */
    @Override
    public DashboardStatistics getDashboardStatistics() {
        long merchantCount = merchantRepository.totalMerchantCount();
        long affiliateCount = affiliateRepository.totalAffiliateCount();
        long campaignCount = campaignRepository.totalCampaignCount();
        return DashboardStatistics.builder()
                .totalMerchantCount(merchantCount)
                .totalCampaignCount(campaignCount)
                .totalAffiliateCount(affiliateCount)
                .build();
    }
}
