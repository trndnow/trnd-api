package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.CampaignAffiliateDto;
import com.trnd.trndapi.entity.CampaignAffiliate;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {MerchantMapper.class, CampaignMapper.class, AffiliateMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CampaignAffiliateMapper {
    CampaignAffiliate toEntity(CampaignAffiliateDto campaignAffiliateDto);

    CampaignAffiliateDto toDto(CampaignAffiliate campaignAffiliate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CampaignAffiliate partialUpdate(CampaignAffiliateDto campaignAffiliateDto, @MappingTarget CampaignAffiliate campaignAffiliate);
}