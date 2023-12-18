package com.trnd.trndapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.trnd.trndapi.entity.CampaignCategoryRef}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignCategoryRefDto implements Serializable {
    private long campCatId;
    @NotNull
    private String campCatNm;
    private String descr;
}