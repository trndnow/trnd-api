package com.trnd.trndapi.commons.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.trnd.trndapi.commons.entity.BusinessServiceCategoryRef}
 */
@Value
public class BusinessServiceCategoryRefDto implements Serializable {
    Long busSvcCatId;
    String busSvcCatNm;
}