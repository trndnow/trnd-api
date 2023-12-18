package com.trnd.trndapi.entity;

import com.trnd.trndapi.audit.Auditable;
import com.trnd.trndapi.enums.ProfileStatus;
import com.trnd.trndapi.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "merch_unique_link"),
        @UniqueConstraint(columnNames = "merch_unique_code"),
        @UniqueConstraint(columnNames = "merch_pri_contact_email")
})
public class Merchant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merch_id")
    private long merchId;
    @Column(name = "merch_nm", unique = true)
    private String merchNm;
    @Column(name = "merch_descr")
    private String merchDescr;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_svc_cat_id", referencedColumnName = "bus_svc_cat_id")
    private BusinessServiceCategoryRef businessServiceCategoryRef;
    @Column(name = "merch_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus merchStatus;
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(name = "merch_unique_link",unique = true)
    private String merchUniqueLink;
    @Column(name = "merch_qr_code",unique = true, length = 1000)
    private String merchQrCode;
    @Column(name = "merch_unique_code",unique = true)
    private String merchUniqueCode;
    @Column(name = "merch_addr_in1")
    private String merchAddrIn1;
    @Column(name = "merch_addr_in2")
    private String merchAddrIn2;
    @ManyToOne
    @JoinColumn(name = "addr_id", nullable = true)
    private Address address;
    @Column(name = "merch_pri_contact_first_nm")
    private String merchPriContactFirstNm;
    @Column(name = "merch_pri_contact_last_nm")
    private String merchPriContactLastNm;
    @Column(name = "merch_pri_contact_email", unique = true)
    private String merchPriContactEmail;
    @Column(name = "merch_pri_contact_phone")
    private String merchPriContactPhone;
    @Column(name = "merch_sec_contact_first_nm")
    private String merchSecContactFirstNn;
    @Column(name = "merch_sec_contact_last_nm")
    private String merchSecContactLastNm;
    @Column(name = "merch_sec_contact_email")
    private String merchSecContactEmail;
    @Column(name = "merch_sec_contact_phone")
    private String merchSecContactPhone;
    @Column(name = "merch_activation_dtm")
    private LocalDateTime merchActivationDtm;
    @Column(name = "merch_transact_pri_currency_cd")
    private String merchTransactPriCurrencyCd;
    @Column(name = "merch_transact_sec_currency_cd")
    private String merchTransactSecCurrencyCd;
    @Column(name = "merch_is_deleted_flg")
    private String merchIsDeletedFlg;
    @Column(name = "merchant_code")
    private String merchantCode;
    @Column(name = "profile_status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus profileStatus;
    @Future
    @Column(name = "merch_delete_account_dt")
    private LocalDateTime merchDeleteAccountDt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
