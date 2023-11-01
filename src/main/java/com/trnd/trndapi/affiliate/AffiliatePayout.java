package com.trnd.trndapi.affiliate;

import com.trnd.trndapi.campaign.entity.CampaignAffiliateProspect;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "affiliate_payout")
public class AffiliatePayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long aff_payout_id;
    @OneToOne
    private CampaignAffiliateProspect camp_aff_pros_id;
    private LocalDate payout_period_start_date;
    private LocalDate payout_period_end_date;
    private int nbr_of_leads;
    private int payout_per_lead;
    private int nbr_of_sale_conversions;
    private int sale_amount;
    private int commission_pct;
    private int commission_amt;
    private String payout_currency_cd;
    private int payout_amount;

}
