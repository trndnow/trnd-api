package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignCategoryRefDto;
import com.trnd.trndapi.entity.CampaignCategoryRef;
import com.trnd.trndapi.mapper.CampaignCategoryRefMapper;
import com.trnd.trndapi.repository.CampaignCategoryRefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignCategoryRefServiceImpl implements CampaignCategoryRefService{
    private final CampaignCategoryRefRepository campaignCategoryRefRepository;
    private final CampaignCategoryRefMapper campaignCategoryRefMapper;

    /**
     * @param name
     * @return
     */
    @Override
    public CampaignCategoryRefDto getCampaignCategoryByName(String name) {
        CampaignCategoryRef byCampCatNm = campaignCategoryRefRepository.findByCampCatNm(name);
        return campaignCategoryRefMapper.toDto(byCampCatNm);
    }
}
