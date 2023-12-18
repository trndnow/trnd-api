package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    @Query("SELECT c FROM Campaign c WHERE c.merchant.merchUniqueCode = : merchantCode")
    Optional<Campaign> findByMerchantCode(String merchantCode);

    @Query("SELECT c FROM Campaign c WHERE c.merchant.merchUniqueCode = : merchantCode AND c.campPrivateNm = : defaultCampaignName")
    Optional<Campaign> findByMerchantCodeDefaultCamp(String merchantCode, String defaultCampaignName);
}