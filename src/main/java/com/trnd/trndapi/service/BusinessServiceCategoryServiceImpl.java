package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.BusinessServiceCategoryRefDto;
import com.trnd.trndapi.entity.BusinessServiceCategoryRef;
import com.trnd.trndapi.mapper.BusinessServiceCategoryRefMapper;
import com.trnd.trndapi.repository.BusinessServiceCategoryRefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusinessServiceCategoryServiceImpl implements BusinessServiceCategoryService{

    private final BusinessServiceCategoryRefRepository businessServiceCategoryRefRepository;
    private final BusinessServiceCategoryRefMapper businessServiceCategoryRefMapper;
    @Override
    public BusinessServiceCategoryRefDto addBusinessServiceCategory(BusinessServiceCategoryRefDto businessServiceCategoryRefDto) {
        BusinessServiceCategoryRef serviceCategoryRef = businessServiceCategoryRefRepository.save(businessServiceCategoryRefMapper.toEntity(businessServiceCategoryRefDto));
        return businessServiceCategoryRefMapper.toDto(serviceCategoryRef);
    }

    @Override
    public List<BusinessServiceCategoryRefDto> viewAllBusinessServiceCategory() {
       return businessServiceCategoryRefMapper.toDtoList(businessServiceCategoryRefRepository.findAll());
    }

    @Override
    public BusinessServiceCategoryRefDto viewBusinessServiceCategory() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public BusinessServiceCategoryRefDto getDefaultBusinessCategory() {
        BusinessServiceCategoryRef businessServiceCategoryRef = businessServiceCategoryRefRepository.findByBusSvcCatNmIgnoreCase("Default").orElseThrow(() -> new RuntimeException("Error: Business category not found."));
        return businessServiceCategoryRefMapper.toDto(businessServiceCategoryRef);
    }
}
