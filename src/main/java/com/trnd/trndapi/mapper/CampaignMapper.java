package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.entity.Campaign;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CampaignMapper {
    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    Campaign toEntity(CampaignDto campaignDto);

    CampaignDto toDto(Campaign campaign);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Campaign partialUpdate(CampaignDto campaignDto, @MappingTarget Campaign campaign);

    List<CampaignDto> toDtoList(List<Campaign> campaignList);
}