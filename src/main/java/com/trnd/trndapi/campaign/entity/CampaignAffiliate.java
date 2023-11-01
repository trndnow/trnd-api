package com.trnd.trndapi.campaign.entity;

import com.trnd.trndapi.affiliate.Affiliate;
import com.trnd.trndapi.merchant.entity.Merchant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campaign_affiliate")
public class CampaignAffiliate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long camp_aff_id;
    @ManyToOne
    @JoinColumn(name = "merch_id")
    private Merchant merch_id;
    @ManyToOne
    @JoinColumn(name = "camp_id")
    private Campaign camp_id;
    @ManyToOne
    @JoinColumn(name = "aff_id")
    private Affiliate aff_id;
    private String camp_aff_is_inactive_flg = String.valueOf('N');
    private LocalDateTime camp_aff_start_date;
    private LocalDateTime camp_aff_end_date;
    private String camp_aff_is_deleted_flg = String.valueOf('N');
    private String camp_aff_unique_link;
    private String camp_aff_qr_code;
    private String camp_aff_unique_code;
    private LocalDateTime rec_insert_dtm;
    private String rec_insert_by;
    private LocalDateTime rec_update_dtm;
    private String rec_update_by;

}
