package com.trnd.trndapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trnd.trndapi.annotation.ValidIncentiveMethod;
import com.trnd.trndapi.annotation.ValidUnit;
import com.trnd.trndapi.enums.CampStatus;
import com.trnd.trndapi.enums.CampType;
import com.trnd.trndapi.enums.IncentiveMethod;
import com.trnd.trndapi.enums.Unit;
import com.trnd.trndapi.serializer.LocalDateSerializer;
import com.trnd.trndapi.serializer.LocalDateTimeSerializer;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.trnd.trndapi.entity.Campaign}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignDto implements Serializable{

    private long campId;
    private MerchantDto merchant;
    private String campPrivateNm;
    private String publicNm;
    private CampStatus campStatus;
    private String campDescr;
    private CampType campType;
    private CampaignCategoryRefDto campaignCategoryRef;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate campStartDt;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate campEndDt;
    private String campGoalsDescr;
    private int affBaseIncentivePerRefer;
    @ValidUnit
    private Unit affBaseIncentivePerReferUnit;
    private int affIncentivePerRefer;
    @ValidUnit
    private Unit affIncentivePerReferUnit;
    private int affIncentivePerSale;
    @ValidUnit
    private Unit affIncentivePerSaleUnit;
    private int minimumSalePriceForCoupon;
    private int campApproxCostPerTransact;
    private int campBudget;
    private int maxAffAllowed;
    private int maxProsSubmitAllowed;
    private int maxProsSubmitAllowedPerAff;
    @ValidIncentiveMethod
    private IncentiveMethod prosIncentiveMethod;
    private int prosIncentivePerTransact;
    @ValidUnit
    private Unit prosIncentivePerTransactUnit;
    @Max(10000)
    private int uniqueCouponCountTotal;
    private int uniqueCouponCountPerAff;
    private int maxCouponUseAllowed;
    private int maxCouponUseAllowedDaily;
    private int maxCouponUseAllowedPerPros;
    private int maxCouponUseAllowedDailyPerPros;
    private int campIsDeletedFlg;
    private String createdBy;
    private String modifiedBy;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime modifiedDate;
}