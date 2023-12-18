package com.trnd.trndapi.repository;

import com.trnd.trndapi.entity.CampaignCategoryRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignCategoryRefRepository extends JpaRepository<CampaignCategoryRef,Long> {
    CampaignCategoryRef findByCampCatNm(String name);
}
