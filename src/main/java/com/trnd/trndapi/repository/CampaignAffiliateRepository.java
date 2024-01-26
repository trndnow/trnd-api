package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.CampaignAffiliate;
import com.trnd.trndapi.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignAffiliateRepository extends JpaRepository<CampaignAffiliate, Long> {
    @Query("select c from CampaignAffiliate c where c.merchant.merchId = ?1 and c.affiliate.user.userStatus = ?2")
    List<CampaignAffiliate> findByMerchant_MerchIdAndAffiliate_User_UserStatus(long merchId, AccountStatus userStatus);
}