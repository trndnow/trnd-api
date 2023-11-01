package com.trnd.trndapi.merchant.mapper;

import com.trnd.trndapi.commons.mapper.AddressCityRefMapper;
import com.trnd.trndapi.commons.mapper.BusinessServiceCategoryRefMapper;
import com.trnd.trndapi.commons.mapper.TimezoneRefMapper;
import com.trnd.trndapi.merchant.dto.MerchantDto;
import com.trnd.trndapi.merchant.entity.Merchant;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BusinessServiceCategoryRefMapper.class, AddressCityRefMapper.class, TimezoneRefMapper.class})
public interface MerchantMapper {
    MerchantMapper INSTANCE = Mappers.getMapper(MerchantMapper.class);
    Merchant toEntity(MerchantDto merchantDto);

    @AfterMapping
    default void linkProductMerchantSubscriptions(@MappingTarget Merchant merchant) {
        merchant.getProductMerchantSubscriptions().forEach(productMerchantSubscription -> productMerchantSubscription.setMerch_id(merchant));
    }

    MerchantDto toDto(Merchant merchant);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Merchant partialUpdate(MerchantDto merchantDto, @MappingTarget Merchant merchant);
}
