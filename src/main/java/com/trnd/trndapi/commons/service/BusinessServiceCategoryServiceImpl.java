package com.trnd.trndapi.commons.service;

import com.trnd.trndapi.commons.dto.BusinessServiceCategoryRefDto;
import com.trnd.trndapi.commons.entity.BusinessServiceCategoryRef;
import com.trnd.trndapi.commons.mapper.BusinessServiceCategoryRefMapper;
import com.trnd.trndapi.commons.repository.BusinessServiceCategoryRefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusinessServiceCategoryServiceImpl implements BusinessServiceCategoryService{

    private final BusinessServiceCategoryRefRepository businessServiceCategoryRefRepository;
    @Override
    public BusinessServiceCategoryRefDto addBusinessServiceCategory(BusinessServiceCategoryRefDto businessServiceCategoryRefDto) {
        BusinessServiceCategoryRef serviceCategoryRef = businessServiceCategoryRefRepository.save(BusinessServiceCategoryRefMapper.INSTANCE.toEntity(businessServiceCategoryRefDto));
        return BusinessServiceCategoryRefMapper.INSTANCE.toDto(serviceCategoryRef);
    }

    @Override
    public List<BusinessServiceCategoryRefDto> viewAllBusinessServiceCategory() {
       return BusinessServiceCategoryRefMapper.INSTANCE.toDto(businessServiceCategoryRefRepository.findAll());
    }

    @Override
    public BusinessServiceCategoryRefDto viewBusinessServiceCategory() {
        return null;
    }
}
