package com.trnd.trndapi.commons.mapper;

import com.trnd.trndapi.commons.dto.BusinessServiceCategoryRefDto;
import com.trnd.trndapi.commons.entity.BusinessServiceCategoryRef;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BusinessServiceCategoryRefMapper {

    BusinessServiceCategoryRefMapper INSTANCE = Mappers.getMapper(BusinessServiceCategoryRefMapper.class);

    BusinessServiceCategoryRef toEntity(BusinessServiceCategoryRefDto businessServiceCategoryRefDto);

    BusinessServiceCategoryRefDto toDto(BusinessServiceCategoryRef businessServiceCategoryRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BusinessServiceCategoryRef partialUpdate(BusinessServiceCategoryRefDto businessServiceCategoryRefDto, @MappingTarget BusinessServiceCategoryRef businessServiceCategoryRef);

    List<BusinessServiceCategoryRefDto> toDto(List<BusinessServiceCategoryRef> all);
}
