package com.trnd.trndapi.campaign.entity;

import com.trnd.trndapi.campaign.enums.CampStatus;
import com.trnd.trndapi.campaign.enums.CampType;
import com.trnd.trndapi.merchant.entity.Merchant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long camp_id;
    @ManyToOne
    @JoinColumn(name = "merch_id")
    private Merchant merch_id;
    private String camp_private_nm;
    private String public_nm;
    private CampStatus camp_status;
    private String camp_descr;
    private CampType camp_type;
    @ManyToOne
    @JoinColumn(name = "camp_cat_id")
    private CampaignCategoryRef camp_cat_id;
    private LocalDate camp_start_dt;
    private LocalDate camp_end_dt;
    private String camp_goals_descr;
    private int aff_incentive_per_refer;
    private String aff_incentive_per_refer_unit;
    private int aff_incentive_per_sale;
    private String aff_incentive_per_sale_unit;
    private String pros_incentive_method;
    private int pros_incentive_per_transact_unit;
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
