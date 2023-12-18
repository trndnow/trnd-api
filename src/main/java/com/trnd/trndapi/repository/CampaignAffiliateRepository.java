package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.CampaignAffiliate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignAffiliateRepository extends JpaRepository<CampaignAffiliate, Long> {
}