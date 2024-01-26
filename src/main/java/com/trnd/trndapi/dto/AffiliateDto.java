package com.trnd.trndapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trnd.trndapi.enums.AffiliateStatus;
import com.trnd.trndapi.enums.ProfileStatus;
import com.trnd.trndapi.serializer.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.trnd.trndapi.entity.Affiliate}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AffiliateDto implements Serializable {
    private String createdBy;
    private String modifiedBy;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifiedDate;
    private long affId;
    private String affFirstNm;
    private String affLastNm;
    private String affIsDeletedFlg;
    private String affUniqueLink;
    private String affQrCode;
    private String affUniqueCode;
    private String affContactEmail;
    private String affContactEmailOpt;
    private String affContactPhone;
    private String affAddrLn1;
    private String affAddrLn2;
    private AddressDto addressDto;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime affActivationDtm;
    private AffiliateStatus affStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime affLastActivityDtm;
    private String affPayoutPriCurrencyCd;
    private String affPayoutSecCurrencyCd;
    private ProfileStatus profileStatus;
    private String affiliateCode;
    private UserDto user;
}