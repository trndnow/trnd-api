package com.trnd.trndapi.commons.mapper;

import com.trnd.trndapi.commons.dto.TimezoneRefDto;
import com.trnd.trndapi.commons.entity.TimezoneRef;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TimezoneRefMapper {
    TimezoneRefMapper INSTANCE = Mappers.getMapper(TimezoneRefMapper.class);

    TimezoneRefDto convertToDto(TimezoneRef timezoneRef);

    TimezoneRef convertToEntity(TimezoneRefDto timezoneRefDto);

    List<TimezoneRef> convertToEntity(List<TimezoneRefDto> timezoneRefDtoList);

    List<TimezoneRefDto> convertToDto(List<TimezoneRef> timezoneRefList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TimezoneRef partialUpdate(TimezoneRefDto timezoneRefDto, @MappingTarget TimezoneRef timezoneRef);
}
