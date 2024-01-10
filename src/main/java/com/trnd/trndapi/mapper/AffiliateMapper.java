package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.entity.Affiliate;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AffiliateMapper {

    Affiliate toEntity(AffiliateDto affiliateDto);

    AffiliateDto toDto(Affiliate affiliate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Affiliate partialUpdate(AffiliateDto affiliateDto, @MappingTarget Affiliate affiliate);

    List<AffiliateDto> toDtoList(List<Affiliate> affiliates);
}