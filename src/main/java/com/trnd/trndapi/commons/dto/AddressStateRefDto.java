package com.trnd.trndapi.commons.dto;

import com.trnd.trndapi.commons.dto.AddressCountryRefDto;
import com.trnd.trndapi.commons.entity.AddressStateRef;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link AddressStateRef}
 */
@Value
public class AddressStateRefDto implements Serializable {
    long addr_state_id;
    @NotNull
    AddressCountryRefDto addr_cntry_id;
    String addr_state_nm;
}