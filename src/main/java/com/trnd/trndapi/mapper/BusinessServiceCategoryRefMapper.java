package com.trnd.trndapi.mapper;

import com.trnd.trndapi.dto.BusinessServiceCategoryRefDto;
import com.trnd.trndapi.entity.BusinessServiceCategoryRef;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessServiceCategoryRefMapper {
    BusinessServiceCategoryRefMapper INSTANCE = Mappers.getMapper(BusinessServiceCategoryRefMapper.class);

    BusinessServiceCategoryRef toEntity(BusinessServiceCategoryRefDto businessServiceCategoryRefDto);

    BusinessServiceCategoryRefDto toDto(BusinessServiceCategoryRef businessServiceCategoryRef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BusinessServiceCategoryRef partialUpdate(BusinessServiceCategoryRefDto businessServiceCategoryRefDto, @MappingTarget BusinessServiceCategoryRef businessServiceCategoryRef);

    List<BusinessServiceCategoryRefDto> toDtoList(List<BusinessServiceCategoryRef> businessServiceCategoryRefs);

}