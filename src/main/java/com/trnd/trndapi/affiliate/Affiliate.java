package com.trnd.trndapi.affiliate;

import com.trnd.trndapi.commons.entity.AddressCityRef;
import com.trnd.trndapi.commons.entity.AddressCountryRef;
import com.trnd.trndapi.commons.entity.AddressStateRef;
import com.trnd.trndapi.commons.entity.TimezoneRef;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
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
public class Affiliate {
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
    @Column(name = "aff_qr_code")
    private String affQrCode;
    @Column(name = "aff_unique_code")
    private String affUniqueCode;
    @Column(name = "aff_contact_email")
    private String affContactEmail;
    @Column(name = "aff_contact_phone")
    private String affContactPhone;
    @Column(name = "aff_addr_ln1")
    private String affAddrLn1;
    @Column(name = "aff_addr_ln2")
    private String affAddrLn2;
    @ManyToOne
    @JoinColumn(name = "addr_city_id")
    private AddressCityRef addrCityId;
    @ManyToOne
    @JoinColumn(name = "addr_state_id")
    private AddressStateRef addrStateId;
    @Column(name = "aff_addr_zip")
    private String affAddrZip;
    @ManyToOne
    @JoinColumn(name = "addr_cntry_id")
    private AddressCountryRef addrCntryId;
    @ManyToOne
    @JoinColumn(name = "timezone_id")
    private TimezoneRef timezoneId;
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
    @Column(name = "rec_insert_dtm")
    private LocalDateTime recInsertDtm;
    @Column(name = "recInsertBy")
    private String recInsertBy;
    @Column(name = "rec_update_dtm")
    private LocalDateTime recUpdateDtm;
    @Column(name = "rec_update_by")
    private String recUpdateBy;


}
