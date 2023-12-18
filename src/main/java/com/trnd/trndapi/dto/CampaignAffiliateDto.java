package com.trnd.trndapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trnd.trndapi.serializer.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.trnd.trndapi.entity.CampaignAffiliate}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignAffiliateDto implements Serializable {
    private String createdBy;
    private String modifiedBy;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifiedDate;
    private long campAffId;
    private MerchantDto merchant;
    private CampaignDto campaign;
    private AffiliateDto affiliate;
    private String campAffIsInactiveFlg;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime campAffStartDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime campAffEndDate;
    private String campAffIsDeletedFlg;
    private String campAffUniqueLink;
    private String campAffQrCode;
    private String campAffUniqueCode;
}