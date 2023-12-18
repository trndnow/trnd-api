package com.trnd.trndapi.entity;

import com.trnd.trndapi.entity.CampaignAffiliateProspect;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "affiliate_payout")
public class AffiliatePayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aff_payout_id")
    private long AffPayoutId;
    @OneToOne
    @JoinColumn(name = "camp_aff_pros_id")
    private CampaignAffiliateProspect campAffProsId;
    @Column(name = "payout_period_start_date")
    private LocalDate payoutPeriodStartDate;
    @Column(name = "payout_period_end_date")
    private LocalDate payoutPeriodEndDate;
    @Column(name = "nbr_of_leads")
    private int nbrOfLeads;
    @Column(name = "payout_per_lead")
    private int payoutPerLead;
    @Column(name = "nbr_of_sale_conversions")
    private int nbrOfSaleConversions;
    @Column(name = "sale_amount")
    private int saleAmount;
    @Column(name = "commission_pct")
    private int commissionPct;
    @Column(name = "commission_amt")
    private int commissionAmt;
    @Column(name = "payout_currency_cd")
    private String payoutCurrencyCd;
    private int payout_amount;

}
