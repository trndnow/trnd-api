package com.trnd.trndapi.dto;

import com.trnd.trndapi.entity.Address;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
    private long id;
    private String stateCode;
    private String city;
    private int zipcode;
    private String timezone;
}