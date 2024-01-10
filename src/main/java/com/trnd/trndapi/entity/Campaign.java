package com.trnd.trndapi.entity;

import com.trnd.trndapi.audit.Auditable;
import com.trnd.trndapi.enums.CampStatus;
import com.trnd.trndapi.enums.CampType;
import com.trnd.trndapi.enums.IncentiveMethod;
import com.trnd.trndapi.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"merch_id","camp_cat_id","camp_start_dt"})
})
public class Campaign extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camp_id")
    private long campId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merch_id")
    private Merchant merchant;
    @Column(name = "camp_private_nm")
    private String campPrivateNm;
    @Column(name = "public_nm")
    private String publicNm;
    @Enumerated(EnumType.STRING)
    @Column(name = "camp_status")
    private CampStatus campStatus;
    @Column(name = "camp_descr")
    private String campDescr;
    @Enumerated(EnumType.STRING)
    @Column(name = "camp_type")
    private CampType campType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_cat_id",unique = false)
    private CampaignCategoryRef campaignCategoryRef;
    @Column(name = "camp_start_dt")
    private LocalDate campStartDt;
    @Column(name = "camp_end_dt")
    private LocalDate campEndDt;
    @Column(name = "camp_goals_descr")
    private String campGoalsDescr;
    @Column(name = "aff_base_incentive_per_refer")
    private int affBaseIncentivePerRefer;
    @Enumerated(EnumType.STRING)
    @Column(name = "aff_base_incentive_per_refer_unit")
    private Unit affBaseIncentivePerReferUnit;
    @Column(name = "aff_incentive_per_refer")
    private int affIncentivePerRefer;
    @Enumerated(EnumType.STRING)
    @Column(name = "aff_incentive_per_refer_unit")
    private Unit affIncentivePerReferUnit;
    @Column(name = "aff_incentive_per_sale")
    private int affIncentivePerSale;
    @Enumerated(EnumType.STRING)
    @Column(name = "aff_incentive_per_sale_unit")
    private Unit affIncentivePerSaleUnit;
    @Column(name = "minimum_sale_price_for_coupon")
    private int minimumSalePriceForCoupon;
    @Column(name = "camp_approx_cost_per_transact")
    private int campApproxCostPerTransact;
    @Column(name = "camp_budget")
    private int campBudget;
    @Column(name = "max_aff_allowed")
    private int maxAffAllowed;
    @Column(name = "max_pros_submit_allowed")
    private int maxProsSubmitAllowed;
    @Column(name = "max_pros_submit_allowed_per_aff")
    private int maxProsSubmitAllowedPerAff;
    @Enumerated(EnumType.STRING)
    @Column(name = "pros_incentive_method")
    private IncentiveMethod prosIncentiveMethod;
    @Column(name = "pros_incentive_per_transact")
    private int prosIncentivePerTransact;
    @Enumerated(EnumType.STRING)
    @Column(name = "pros_incentive_per_transact_unit")
    private Unit prosIncentivePerTransactUnit;
    @Max(value = 10000)
    @Column(name = "unique_coupon_count_total")
    private int uniqueCouponCountTotal = 10000;
    @Column(name = "unique_coupon_count_per_aff")
    private int uniqueCouponCountPerAff;
    @Column(name = "max_coupon_use_allowed")
    private int maxCouponUseAllowed;
    @Column(name = "max_coupon_use_allowed_daily")
    private int maxCouponUseAllowedDaily;
    @Column(name = "max_coupon_use_allowed_per_pros")
    private int maxCouponUseAllowedPerPros;
    @Column(name = "max_coupon_use_allowed_daily_per_pros")
    private int maxCouponUseAllowedDailyPerPros;
    @Column(name = "camp_is_deleted_flg")
    private int campIsDeletedFlg;
}
