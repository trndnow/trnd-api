package com.trnd.trndapi.merchant.entity;

import com.trnd.trndapi.audit.Auditable;
import com.trnd.trndapi.commons.entity.*;
import com.trnd.trndapi.subscription.entity.ProductMerchantSubscription;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "merch_nm"),
        @UniqueConstraint(columnNames = "merch_unique_link"),
        @UniqueConstraint(columnNames = "merch_unique_code"),
        @UniqueConstraint(columnNames = "merch_pri_contact_email")
})
public class Merchant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long merch_id;
    @NotEmpty
    @Column(unique = true)
    private String merch_nm;
    private String merch_descr;
    @ManyToOne
    private BusinessServiceCategoryRef bus_svc_cat_id;
    private String merch_status;
    private String logo_url;
    @Column(unique = true)
    private String merch_unique_link;
    @Column(unique = true)
    private String merch_qr_code;
    @Column(unique = true)
    private String merch_unique_code;
    @NotEmpty
    private String merch_addr_in1;
    private String merch_addr_in2;
    @ManyToOne
    private AddressCityRef addr_city_id;
    private String merch_addr_zip;
    @ManyToOne
    private TimezoneRef timezone_id;
    private String merch_pri_contact_first_nm;
    private String merch_pri_contact_last_nm;
    @Column(unique = true)
    private String merch_pri_contact_email;
    private String merch_pri_contact_phone;
    private String merch_sec_contact_first_nm;
    private String merch_sec_contact_last_nm;
    private String merch_sec_contact_email;
    private String merch_sec_contact_phone;
    private LocalDateTime merch_activation_dtm;
    private String merch_transact_pri_currency_cd;
    private String merch_transact_sec_currency_cd;
    private String merch_is_deleted_flg;
    @OneToMany(mappedBy = "merch_id")
    private List<ProductMerchantSubscription> productMerchantSubscriptions = new ArrayList<>();


}
