package com.trnd.trndapi.commons.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link com.trnd.trndapi.commons.entity.AddressCityRef}
 */
@Value
public class AddressCityRefDto implements Serializable {
    long addr_city_id;
    @NotNull
    AddressStateRefDto addr_state_id;
    String addr_city_nm;
    @Length(min = 5, max = 5)
    String zipcode;
}