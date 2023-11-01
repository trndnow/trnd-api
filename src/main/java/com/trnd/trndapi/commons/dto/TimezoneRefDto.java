package com.trnd.trndapi.commons.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.trnd.trndapi.commons.entity.TimezoneRef}
 */
@Value
public class TimezoneRefDto implements Serializable {
    long timezone_id;
    @NotEmpty(message = "Timezone name must not be empty")
    String timezone_nm;
    @NotEmpty(message = "Abbreviation must not be empty")
    String abbreviation;
    @NotEmpty(message = "UTC offset must not be empty")
    String utc_offset;
    @NotEmpty(message = "Country must not be empty")
    String country;
    @NotEmpty(message = "Region must not be empty")
    String region;
}