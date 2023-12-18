package com.trnd.trndapi.entity;

import com.trnd.trndapi.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "campaign_affiliate")
public class CampaignAffiliate extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camp_aff_id")
    private long campAffId;
    @ManyToOne
    @JoinColumn(name = "merch_id")
    private Merchant merchant;
    @ManyToOne
    @JoinColumn(name = "camp_id")
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "aff_id")
    private Affiliate affiliate;
    @Column(name = "camp_aff_is_inactive_flg")
    private String campAffIsInactiveFlg = String.valueOf('N');
    @Column(name = "camp_aff_start_date")
    private LocalDateTime campAffStartDate;
    @Column(name = "camp_aff_end_date")
    private LocalDateTime campAffEndDate;
    @Column(name = "camp_aff_is_deleted_flg")
    private String campAffIsDeletedFlg = String.valueOf('N');
    @Column(name = "camp_aff_unique_link")
    private String campAffUniqueLink;
    @Column(name = "camp_aff_qr_code", length = 1000)
    private String campAffQrCode;
    @Column(name = "camp_aff_unique_code")
    private String campAffUniqueCode;

}
