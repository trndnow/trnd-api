package com.trnd.trndapi.entity;

import com.trnd.trndapi.audit.Auditable;
import com.trnd.trndapi.enums.AffiliateStatus;
import com.trnd.trndapi.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "affiliate", uniqueConstraints = {
        @UniqueConstraint(columnNames = "aff_unique_link"),
        @UniqueConstraint(columnNames = "aff_qr_code"),
        @UniqueConstraint(columnNames = "aff_unique_code"),
        @UniqueConstraint(columnNames = "aff_contact_email"),
        @UniqueConstraint(columnNames = "aff_contact_phone")
})
public class Affiliate extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aff_id")
    private long affId;
    @Column(name = "aff_first_nm")
    private String affFirstNm;
    @Column(name = "aff_last_nm")
    private String affLastNm;
    @Column(name = "aff_is_deleted_flg")
    private String affIsDeletedFlg;
    @Column(name = "aff_unique_link")
    private String affUniqueLink;
    @Column(name = "aff_qr_code", length = 1000)
    private String affQrCode;
    @Column(name = "aff_unique_code")
    private String affUniqueCode;
    @Column(name = "aff_contact_email")
    private String affContactEmail;
    @Column(name = "aff_contact_email_opt")
    private String affContactEmailOpt;
    @Column(name = "aff_contact_phone")
    private String affContactPhone;
    @Column(name = "aff_addr_ln1")
    private String affAddrLn1;
    @Column(name = "aff_addr_ln2")
    private String affAddrLn2;
    @ManyToOne
    @JoinColumn(name = "addr_id",referencedColumnName = "id")
    private Address addrId;
    @Column(name = "aff_activation_dtm")
    private LocalDateTime affActivationDtm;
    @Column(name = "aff_status")
    private AffiliateStatus affStatus;
    @Column(name = "aff_last_activity_dtm")
    private LocalDateTime affLastActivityDtm;
    @Column(name = "aff_payout_pri_currency_cd")
    private String affPayoutPriCurrencyCd;
    @Column(name = "aff_payout_sec_currency_cd")
    private String affPayoutSecCurrencyCd;
    @Column(name = "profile_status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus profileStatus;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
