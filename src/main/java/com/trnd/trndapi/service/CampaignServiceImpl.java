package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignCategoryRefDto;
import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.entity.Campaign;
import com.trnd.trndapi.enums.CampStatus;
import com.trnd.trndapi.enums.CampType;
import com.trnd.trndapi.exception.CampaignNotFoundException;
import com.trnd.trndapi.mapper.CampaignCategoryRefMapper;
import com.trnd.trndapi.mapper.CampaignMapper;
import com.trnd.trndapi.mapper.MerchantMapper;
import com.trnd.trndapi.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService{

    private final CampaignRepository campaignRepository;
    private final CampaignCategoryRefServiceImpl campaignCategoryRefService;
    private final MerchantService merchantService;
    private final CampaignMapper campaignMapper;
    private final MerchantMapper merchantMapper;
    private final CampaignCategoryRefMapper campaignCategoryRefMapper;
    @Override
    public CampaignDto addCampaign(CampaignDto campaignDto) {
        merchantService.getMerchantByID(campaignDto.getMerchant());
        Campaign campaign = campaignMapper.toEntity(campaignDto);
        Campaign savedCampaign = campaignRepository.save(campaign);
        return campaignMapper.toDto(savedCampaign);
    }

    @Override
    public CampaignDto viewCampaign(long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new CampaignNotFoundException("Error: Campaign not found"));
        return campaignMapper.toDto(campaign);
    }

    @Override
    public List<CampaignDto> viewAllCampaign() {
        List<Campaign> campaignList = campaignRepository.findAll();
        return campaignMapper.toDtoList(campaignList);
    }

    /**
     * @param merchantDto
     * @return campaignDto
     */
    @Override
    public CampaignDto defaultCampaignAssociation(MerchantDto merchantDto) {
        //TODO: Create a method to return DefaultCampaign for the given merchant.
      try{
          Campaign campaign =  createDefaultCampaign(merchantDto);
          Campaign saveCampaign = campaignRepository.save(campaign);
          return campaignMapper.toDto(saveCampaign);
      }catch (Exception exception){
          log.error("ERROR: SAVING ASSOCIATING DEFAULT CAMPAIGN");
      }
        return null;
    }

    /**
     * @param merchantCode
     * @return
     */
    @Override
    public CampaignDto getCampaignByMerchantCode(String merchantCode) {
        Optional<Campaign> campaign = campaignRepository.findByMerchantCodeDefaultCamp(merchantCode, "Default");
        if(campaign.isPresent()){
            return campaignMapper.toDto(campaign.get());
        }
        throw new RuntimeException("Error: Default Campaign Not Found for the give merchant code: " + merchantCode);
    }

    /**
     * @param merchantCode
     * @return
     */
    @Override
    public ResponseDto viewCampaignByMerchantCode(String merchantCode) {
        MerchantDto merchantByMerchantCode = merchantService.getMerchantByMerchantCode(merchantCode);
        if(merchantByMerchantCode == null)
            return ResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.toString())
                    .statusMsg("INVALID MERCHANT CODE")
                    .build();

        List<Campaign> campaignByMerchantCode = campaignRepository.findCampaignByMerchantCode(merchantCode);
        if(campaignByMerchantCode.isEmpty())
            return ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("NO RECORD FOUND")
                    .build();

        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.toString())
                .statusMsg("RECORDS FOUND")
                .data(campaignMapper.toDtoList(campaignByMerchantCode))
                .build();
    }

    private Campaign createDefaultCampaign(MerchantDto merchantDto) {
        CampaignCategoryRefDto defaultCampaignCategory = campaignCategoryRefService.getCampaignCategoryByName("Default");
        return Campaign.builder()
                .merchant(merchantMapper.toEntity(merchantDto))
                .campPrivateNm("DEFAULT_CAMPAIGN")
                .campStatus(CampStatus.ACTIVE)
                .campType(CampType.DEFAULT)
                .campaignCategoryRef(campaignCategoryRefMapper.toEntity(defaultCampaignCategory))
                .campStartDt(LocalDate.now())
                .uniqueCouponCountTotal(-1)
                .uniqueCouponCountPerAff(-1)
                .campIsDeletedFlg(0)
                .build();
    }

}
