package com.trnd.trndapi.events;

import com.google.zxing.WriterException;
import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.dto.BusinessServiceCategoryRefDto;
import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.entity.CampaignAffiliate;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.AffiliateStatus;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.enums.ProfileStatus;
import com.trnd.trndapi.mapper.AffiliateMapper;
import com.trnd.trndapi.mapper.CampaignMapper;
import com.trnd.trndapi.mapper.MerchantMapper;
import com.trnd.trndapi.mapper.UserMapper;
import com.trnd.trndapi.service.*;
import com.trnd.trndapi.utils.CommonUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserEventListener {
    private final MerchantService merchantService;
    private final CampaignService campaignService;
    private final AffiliateService affiliateService;
    private final CampaignAffiliateService campaignAffiliateService;
    public final BusinessServiceCategoryService businessServiceCategoryService;
    private final CodeGeneratorService codeGeneratorService;
    private final UserMapper userMapper;
    private final MerchantMapper merchantMapper;
    private final CampaignMapper campaignMapper;
    private final AffiliateMapper affiliateMapper;

    @EventListener
    @Async
    public void handleUserCreatedEvent(UserCreatedEvent event) throws IOException, WriterException {
        log.info("User created event handler");
        log.info(event.getUser().toString());
        if(event.getUser().getRole().getName().equals(ERole.ROLE_MERCHANT)){
            String merchantUniqueCode = event.getUser().getUniqueId().toString();
            String merchantUniqueLink = CommonUtils.createCampaignUniqueLink(merchantUniqueCode);
            String qrCodeImageAsBase64 = CommonUtils.generateQRCodeImageAsBase64(merchantUniqueLink, 250, 250);
           BusinessServiceCategoryRefDto businessServiceCategoryRefDto = businessServiceCategoryService.getDefaultBusinessCategory();
            MerchantDto merchantDto = MerchantDto.builder()
                    .merchNm(event.getSignupRequest().getMerchantName())
                    .merchantCode(event.getUser().getUserCode())
                    .merchUniqueCode(merchantUniqueCode)
                    .merchUniqueLink(merchantUniqueLink)
                    .merchQrCode(qrCodeImageAsBase64)
                    .businessServiceCategoryRefDto(businessServiceCategoryRefDto)
                    .merchPriContactEmail(event.getUser().getEmail())
                    .merchPriContactPhone(event.getUser().getMobile())
                    .merchStatus(AccountStatus.INACTIVE)
                    .profileStatus(ProfileStatus.INCOMPLETE)
                    .createdDate(LocalDateTime.now())
                    .user(userMapper.toDto(event.getUser()))
                    .build();
            log.info("Creating Merchant Entry");
            merchantService.createMerchant(merchantDto);
        } else if (event.getUser().getRole().getName().equals(ERole.ROLE_AFFILIATE)) {

            onBoardAffiliate(event);

            log.info("Creating Affiliate Entry");

        } else {
            log.info("LOGGING FROM: {}", MerchantEventListener.class.getName());
            log.info("ROLE IS NOT MERCHANT ");
        }
    }

    @Transactional
    public void onBoardAffiliate(UserCreatedEvent event) throws WriterException, IOException {
        MerchantDto merchantDto = merchantService.getMerchantByMerchantCode(event.getSignupRequest().getMerchantCode());
        AffiliateDto affiliateDto = AffiliateDto.builder()
                .affContactEmail(event.getUser().getEmail())
                .affContactPhone(event.getUser().getMobile())
                .affUniqueCode(event.getUser().getUniqueId().toString())
                .affUniqueLink(generateUniqueLink(event.getUser().getUniqueId().toString()))
                .affStatus(AffiliateStatus.INACTIVE)
                .profileStatus(ProfileStatus.INCOMPLETE)
                .createdDate(LocalDateTime.now())
                .user(userMapper.toDto(event.getUser()))
                .build();
        AffiliateDto savedAffiliateDto =  affiliateService.createAffiliate(affiliateDto,merchantDto);



        CampaignDto campaignDto = campaignService.getCampaignByMerchantID(merchantDto.getMerchId());
        String campaignUniqueCode = CommonUtils.generateUniqueCode();
        String campaignUniqueLink = CommonUtils.createCampaignUniqueLink(campaignUniqueCode);
        String qrCodeImageAsBase64 = CommonUtils.generateQRCodeImageAsBase64(campaignUniqueLink, 250, 250);

        CampaignAffiliate campaignAffiliate = CampaignAffiliate.builder()
                .campaign(campaignMapper.toEntity(campaignDto))
                .affiliate(affiliateMapper.toEntity(savedAffiliateDto))
                .merchant(merchantMapper.toEntity(merchantDto))
                .campAffStartDate(LocalDateTime.now())
                .campAffUniqueCode(campaignUniqueCode)
                .campAffUniqueLink(campaignUniqueLink)
                .campAffQrCode(qrCodeImageAsBase64)
                .build();

        campaignAffiliateService.create(campaignAffiliate);
        //TODO: Send email template to Merchant that Affiliate approval is pending.
        //TODO: Send email template to affiliate the Registration is completed & Approval is pending from Merchant.
    }

    /**
     * @param uniqueCode
     * @return
     */
    @Deprecated
    /**
     *  The logic is move to CommonUtils.createMerchantUniqueLink
     */
    private String generateUniqueLink(String uniqueCode) {
        String baseUrl = "http://localhost:9999/referral";
        return baseUrl + "?ref=" + uniqueCode;
    }
}
