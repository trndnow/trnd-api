package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.BusinessServiceCategoryRefDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessServiceCategoryService {
    BusinessServiceCategoryRefDto addBusinessServiceCategory(BusinessServiceCategoryRefDto businessServiceCategoryRefDto);

    List<BusinessServiceCategoryRefDto> viewAllBusinessServiceCategory();
    BusinessServiceCategoryRefDto viewBusinessServiceCategory();

    BusinessServiceCategoryRefDto getDefaultBusinessCategory();
}
