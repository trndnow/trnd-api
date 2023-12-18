package com.trnd.trndapi.dto;

import com.trnd.trndapi.entity.Campaign;
import com.trnd.trndapi.entity.CampaignCategoryRef;
import com.trnd.trndapi.enums.CampStatus;
import com.trnd.trndapi.enums.CampType;
import com.trnd.trndapi.enums.IncentiveMethod;
import com.trnd.trndapi.enums.Unit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link Campaign}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignDto implements Serializable {
    private long camp_id;
    private MerchantDto merchant;
    private String camp_private_nm;
    private String public_nm;
    private CampStatus camp_status;
    private String camp_descr;
    private CampType camp_type;
    private CampaignCategoryRef campaignCategoryRef;
    private LocalDate camp_start_dt;
    private LocalDate camp_end_dt;
    private String camp_goals_descr;
    private int aff_incentive_per_refer;
    @Enumerated(EnumType.STRING)
    private Unit aff_incentive_per_refer_unit;
    private int aff_incentive_per_sale;
    @Enumerated(EnumType.STRING)
    private Unit aff_incentive_per_sale_unit;
    @Enumerated(EnumType.STRING)
    private IncentiveMethod pros_incentive_method;
    @Enumerated(EnumType.STRING)
    private Unit pros_incentive_per_transact_unit;
    private int minimum_sale_price_for_coupon;
    private int camp_approx_cost_per_transact;
    private int camp_budget;
    private int max_aff_allowed;
    private int max_pros_submit_allowed;
    private int max_pros_submit_allowed_per_aff;
    private int unique_coupon_count_total;
    private int unique_coupon_count_per_aff;
    private int unique_coupon_count_generated;
    private int coupon_regenerate_threshold_pct;
    private int max_coupon_use_allowed;
    private int max_coupon_use_allowed_daily;
    private int max_coupon_use_allowed_per_pros;
    private int max_coupon_use_allowed_daily_per_pros;
    private int camp_is_deleted_flg;
    private LocalDateTime rec_insert_dtm;
    private String rec_insert_by;
    private LocalDateTime rec_update_dtm;
    private String rec_update_by;
}