package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    @Query("select c from Campaign c where upper(c.merchant.merchantCode) = upper(?1)")
    List<Campaign> findCampaignByMerchantCode(String merchantCode);
    @Query("select count(c) from Campaign c")
    long totalCampaignCount();
    @Query("SELECT c FROM Campaign c WHERE c.merchant.merchantCode = : merchantCode")
    Optional<Campaign> findByMerchantCode(String merchantCode);

    @Query("SELECT c FROM Campaign c WHERE c.merchant.merchantCode = : merchantCode AND c.campPrivateNm = : defaultCampaignName")
    Optional<Campaign> findByMerchantCodeDefaultCamp(@Param("merchantCode") String merchantCode, @Param("defaultCampaignName") String defaultCampaignName);

    Campaign findByMerchant_MerchantCodeOrMerchant_MerchUniqueCode(String merchantCode, String merchUniqueCode);

    Campaign findByMerchant_MerchIdAndCampaignCategoryRef_CampCatNm(long merchId, String campCatNm);

}