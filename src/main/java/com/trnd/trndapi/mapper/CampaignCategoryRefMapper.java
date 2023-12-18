package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.CampaignCategoryRefDto;
import com.trnd.trndapi.entity.CampaignCategoryRef;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CampaignCategoryRefMapper {
    CampaignCategoryRefMapper INSTANCE = Mappers.getMapper(CampaignCategoryRefMapper.class);
    CampaignCategoryRef toEntity(CampaignCategoryRefDto campaignCategoryRefDto);

    CampaignCategoryRefDto toDto(CampaignCategoryRef campaignCategoryRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CampaignCategoryRef partialUpdate(CampaignCategoryRefDto campaignCategoryRefDto, @MappingTarget CampaignCategoryRef campaignCategoryRef);
}