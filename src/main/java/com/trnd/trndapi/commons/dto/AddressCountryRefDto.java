package com.trnd.trndapi.commons.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.trnd.trndapi.commons.entity.AddressCountryRef}
 */
@Value
public class AddressCountryRefDto implements Serializable {
    long addr_cntry_id;
    String addr_cntry_nm;
}