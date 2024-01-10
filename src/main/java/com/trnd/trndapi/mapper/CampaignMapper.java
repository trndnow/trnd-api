package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.entity.Campaign;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CampaignMapper {
    Campaign toEntity(CampaignDto campaignDto);

    CampaignDto toDto(Campaign campaign);

    List<Campaign> toEntityList(List<CampaignDto> campaignDtoList);

    List<CampaignDto> toDtoList(List<Campaign> campaignList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Campaign partialUpdate(CampaignDto campaignDto, @MappingTarget Campaign campaign);
}