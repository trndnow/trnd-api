package com.trnd.trndapi.commons.mapper;

import com.trnd.trndapi.commons.dto.AddressCityRefDto;
import com.trnd.trndapi.commons.dto.AddressStateRefDto;
import com.trnd.trndapi.commons.entity.AddressCityRef;
import com.trnd.trndapi.commons.entity.AddressStateRef;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AddressCityRefMapper.class})
public interface AddressCityRefMapper {

    AddressCityRefMapper INSTANCE = Mappers.getMapper(AddressCityRefMapper.class);

    AddressCityRefDto convertToDto(AddressCityRef addressCityRef);

    AddressCityRef convertToEntity(AddressCityRefDto addressCityRefDto);

    List<AddressCityRefDto> convertToDto(List<AddressCityRef> addressCityRefList);

    List<AddressCityRef> convertToEntity(List<AddressCityRefDto> addressCityRefDtoList);

    AddressStateRef toEntity(AddressStateRefDto addressStateRefDto);

    AddressStateRefDto toDto(AddressStateRef addressStateRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AddressStateRef partialUpdate(AddressStateRefDto addressStateRefDto, @MappingTarget AddressStateRef addressStateRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AddressCityRef partialUpdate(AddressCityRefDto addressCityRefDto, @MappingTarget AddressCityRef addressCityRef);
}
