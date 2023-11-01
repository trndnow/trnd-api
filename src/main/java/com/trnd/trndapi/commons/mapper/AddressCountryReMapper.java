package com.trnd.trndapi.commons.mapper;

import com.trnd.trndapi.commons.dto.AddressCountryRefDto;
import com.trnd.trndapi.commons.entity.AddressCountryRef;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface AddressCountryReMapper {
    AddressCountryRef toEntity(AddressCountryRefDto addressCountryRefDto);

    AddressCountryRefDto toDto(AddressCountryRef addressCountryRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AddressCountryRef partialUpdate(AddressCountryRefDto addressCountryRefDto, @MappingTarget AddressCountryRef addressCountryRef);
}
