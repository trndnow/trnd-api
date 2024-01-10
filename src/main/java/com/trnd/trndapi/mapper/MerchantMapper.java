package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.entity.Merchant;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BusinessServiceCategoryRefMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MerchantMapper {

    @Mapping(target = "businessServiceCategoryRef", source = "businessServiceCategoryRefDto")
    Merchant toEntity(MerchantDto merchantDto);

    List<Merchant> toEntityList(List<MerchantDto> merchantDtoList);

    @Mapping( target = "businessServiceCategoryRefDto", source = "businessServiceCategoryRef")
    MerchantDto toDto(Merchant merchant);

    List<MerchantDto> toDtoList(List<Merchant> merchantList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Merchant partialUpdate(MerchantDto merchantDto, @MappingTarget Merchant merchant);
}