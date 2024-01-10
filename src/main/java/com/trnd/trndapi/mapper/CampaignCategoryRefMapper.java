package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.CampaignCategoryRefDto;
import com.trnd.trndapi.entity.CampaignCategoryRef;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CampaignCategoryRefMapper {
    CampaignCategoryRef toEntity(CampaignCategoryRefDto campaignCategoryRefDto);

    CampaignCategoryRefDto toDto(CampaignCategoryRef campaignCategoryRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CampaignCategoryRef partialUpdate(CampaignCategoryRefDto campaignCategoryRefDto, @MappingTarget CampaignCategoryRef campaignCategoryRef);
}