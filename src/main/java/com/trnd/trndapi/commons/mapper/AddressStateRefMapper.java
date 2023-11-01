package com.trnd.trndapi.commons.mapper;

import com.trnd.trndapi.commons.dto.AddressCountryRefDto;
import com.trnd.trndapi.commons.entity.AddressCountryRef;
import com.trnd.trndapi.commons.entity.AddressStateRef;
import com.trnd.trndapi.commons.dto.AddressStateRefDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {AddressStateRefMapper.class})
public interface AddressStateRefMapper {
    AddressCountryRef toEntity(AddressCountryRefDto addressCountryRefDto);

    AddressCountryRefDto toDto(AddressCountryRef addressCountryRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AddressCountryRef partialUpdate(AddressCountryRefDto addressCountryRefDto, @MappingTarget AddressCountryRef addressCountryRef);

    AddressStateRef toEntity(AddressStateRefDto addressStateRefDto);

    AddressStateRefDto toDto(AddressStateRef addressStateRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AddressStateRef partialUpdate(AddressStateRefDto addressStateRefDto, @MappingTarget AddressStateRef addressStateRef);
}
