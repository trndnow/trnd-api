package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.CampaignAffiliateDto;
import com.trnd.trndapi.entity.CampaignAffiliate;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MerchantMapper.class, CampaignMapper.class, AffiliateMapper.class})
public interface CampaignAffiliateMapper {
    CampaignAffiliateMapper INSTANCE = Mappers.getMapper(CampaignAffiliateMapper.class);
    CampaignAffiliate toEntity(CampaignAffiliateDto campaignAffiliateDto);

    CampaignAffiliateDto toDto(CampaignAffiliate campaignAffiliate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CampaignAffiliate partialUpdate(CampaignAffiliateDto campaignAffiliateDto, @MappingTarget CampaignAffiliate campaignAffiliate);
}