package com.trnd.trndapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trnd.trndapi.enums.ProfileStatus;
import com.trnd.trndapi.entity.Merchant;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.serializer.LocalDateTimeSerializer;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Merchant}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantDto implements Serializable {
    private String createdBy;
    private String modifiedBy;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifiedDate;
    private long merchId;
    private String merchNm;
    private String merchDescr;
    private BusinessServiceCategoryRefDto businessServiceCategoryRefDto;
    private AccountStatus merchStatus;
    private String logoUrl;
    private String merchUniqueLink;
    private String merchQrCode;
    private String merchUniqueCode;
    private String merchAddrIn1;
    private String merchAddrIn2;
    private AddressDto addressDto;
    private String merchPriContactFirstNm;
    private String merchPriContactLastNm;
    private String merchPriContactEmail;
    private String merchPriContactPhone;
    private String merchSecContactFirstNn;
    private String merchSecContactLastNm;
    private String merchSecContactEmail;
    private String merchSecContactPhone;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime merchActivationDtm;
    private String merchTransactPriCurrencyCd;
    private String merchTransactSecCurrencyCd;
    private String merchIsDeletedFlg;
    private String merchantCode;
    private ProfileStatus profileStatus;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Future
    private LocalDateTime merchDeleteAccountDt;
    private UserDto user;
}