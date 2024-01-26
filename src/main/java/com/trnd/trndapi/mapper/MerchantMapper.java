package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.entity.Merchant;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BusinessServiceCategoryRefMapper.class, AddressMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MerchantMapper {
    @Mapping(target = "address", source = "addressDto")
    @Mapping(target = "businessServiceCategoryRef", source = "businessServiceCategoryRefDto")
    Merchant toEntity(MerchantDto merchantDto);


    @Mapping(target = "addressDto", source = "address")
    @Mapping(target = "businessServiceCategoryRefDto", source = "businessServiceCategoryRef")
    MerchantDto toDto(Merchant merchant);

    List<Merchant> toEntityList(List<MerchantDto> merchantDtoList);

    List<MerchantDto> toDtoList(List<Merchant> merchantList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Merchant partialUpdate(MerchantDto merchantDto, @MappingTarget Merchant merchant);
}