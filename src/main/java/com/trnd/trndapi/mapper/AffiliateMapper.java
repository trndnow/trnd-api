package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.entity.Affiliate;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AffiliateMapper {
    AffiliateMapper INSTANCE = Mappers.getMapper(AffiliateMapper.class);

    Affiliate toEntity(AffiliateDto affiliateDto);

    AffiliateDto toDto(Affiliate affiliate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Affiliate partialUpdate(AffiliateDto affiliateDto, @MappingTarget Affiliate affiliate);
}