package com.trnd.trndapi.commons.service;

import com.trnd.trndapi.commons.dto.BusinessServiceCategoryRefDto;

import java.util.List;

public interface BusinessServiceCategoryService {
    BusinessServiceCategoryRefDto addBusinessServiceCategory(BusinessServiceCategoryRefDto businessServiceCategoryRefDto);

    List<BusinessServiceCategoryRefDto> viewAllBusinessServiceCategory();
    BusinessServiceCategoryRefDto viewBusinessServiceCategory();
}
