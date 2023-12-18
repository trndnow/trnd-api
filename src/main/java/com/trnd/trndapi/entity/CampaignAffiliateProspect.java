package com.trnd.trndapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign_affiliate_prospect")
public class CampaignAffiliateProspect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long camp_aff_pros_id;
    @ManyToOne
    @JoinColumn(name = "camp_aff_id_camp_aff_id")
    private CampaignAffiliate camp_aff_id;
    @ManyToOne
    @JoinColumn(name = "pros_id_pros_id")
    private Prospect pros_id;
    private String lead_flg;
    private String coupon_claim_flg;
    private String sale_convert_flg;
    private String camp_aff_pros_status;
    @ManyToOne
    private Coupon coupon_id;
    private int sale_ref_id;
    private String sale_ref_system;
    private int sale_amt;
    private LocalDateTime sale_dtm;
    private String affiliate_notes;
    private String merch_notes;
    private String contacted_flg;
    private String do_not_pursue_flg;
    private LocalDateTime rec_insert_dtm;
    private String rec_insert_by;
    private LocalDateTime rec_update_dtm;
    private String rec_update_by;
}
