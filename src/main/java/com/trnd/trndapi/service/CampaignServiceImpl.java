package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignCategoryRefDto;
import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.entity.Campaign;
import com.trnd.trndapi.entity.CampaignCategoryRef;
import com.trnd.trndapi.enums.CampStatus;
import com.trnd.trndapi.enums.CampType;
import com.trnd.trndapi.exception.CampaignNotFoundException;
import com.trnd.trndapi.mapper.CampaignCategoryRefMapper;
import com.trnd.trndapi.mapper.CampaignMapper;
import com.trnd.trndapi.mapper.MerchantMapper;
import com.trnd.trndapi.repository.CampaignCategoryRefRepository;
import com.trnd.trndapi.repository.CampaignRepository;
import com.trnd.trndapi.dto.MerchantDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService{

    private final CampaignRepository campaignRepository;
    private final CampaignCategoryRefServiceImpl campaignCategoryRefService;
    private final MerchantService merchantService;
    @Override
    public CampaignDto addCampaign(CampaignDto campaignDto) {
        MerchantDto merchantDto = merchantService.getMerchantByID(campaignDto.getMerchant());
        Campaign  savedCampaign = campaignRepository.save(CampaignMapper.INSTANCE.toEntity(campaignDto));
        return CampaignMapper.INSTANCE.toDto(savedCampaign);
    }

    @Override
    public CampaignDto viewCampaign(long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new CampaignNotFoundException("Error: Campaign not found"));
        return CampaignMapper.INSTANCE.toDto(campaign);
    }

    @Override
    public List<CampaignDto> viewAllCampaign() {
        List<Campaign> campaignList = campaignRepository.findAll();
        return CampaignMapper.INSTANCE.toDtoList(campaignList);
    }

    /**
     * @param merchantDto
     * @return campaignDto
     */
    @Override
    public CampaignDto defaultCampaignAssociation(MerchantDto merchantDto) {
        //TODO: Create a method to return DefaultCampaign for the given merchant.
       Campaign campaign =  createDefaultCampaign(merchantDto);
       Campaign saveCampaign = campaignRepository.save(campaign);
       return CampaignMapper.INSTANCE.toDto(saveCampaign);
    }

    /**
     * @param merchantCode
     * @return
     */
    @Override
    public CampaignDto getCampaignByMerchantCode(String merchantCode) {
        Optional<Campaign> campaign = campaignRepository.findByMerchantCodeDefaultCamp(merchantCode, "Default");
        if(campaign.isPresent()){
            return CampaignMapper.INSTANCE.toDto(campaign.get());
        }
        throw new RuntimeException("Error: Default Campaign Not Found for the give merchant code: " + merchantCode);
    }

    private Campaign createDefaultCampaign(MerchantDto merchantDto) {
        CampaignCategoryRefDto defaultCampaignCategory = campaignCategoryRefService.getCampaignCategoryByName("Default");
        return Campaign.builder()
                .merchant(MerchantMapper.INSTANCE.toEntity(merchantDto))
                .campPrivateNm("DEFAULT_CAMPAIGN")
                .campStatus(CampStatus.ACTIVE)
                .campType(CampType.DEFAULT)
                .campaignCategoryRef(CampaignCategoryRefMapper.INSTANCE.toEntity(defaultCampaignCategory))
                .campStartDt(LocalDate.now())
                .uniqueCouponCountTotal(-1)
                .uniqueCouponCountPerAff(-1)
                .campIsDeletedFlg(0)
                .build();
    }

}
