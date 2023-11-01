package com.trnd.trndapi.merchant.dto;

import com.trnd.trndapi.commons.dto.AddressCityRefDto;
import com.trnd.trndapi.commons.dto.BusinessServiceCategoryRefDto;
import com.trnd.trndapi.commons.dto.TimezoneRefDto;
import com.trnd.trndapi.subscription.entity.ProductMerchantSubscription;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.trnd.trndapi.merchant.entity.Merchant}
 */
@Value
@Builder
public class MerchantDto implements Serializable {
    String createdBy;
    String modifiedBy;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    long merch_id;
    @NotEmpty
    String merch_nm;
    String merch_descr;
    BusinessServiceCategoryRefDto bus_svc_cat_id;
    String merch_status;
    String logo_url;
    String merch_unique_link;
    String merch_qr_code;
    String merch_unique_code;
    @NotEmpty
    String merch_addr_in1;
    String merch_addr_in2;
    AddressCityRefDto addr_city_id;
    String merch_addr_zip;
    TimezoneRefDto timezone_id;
    String merch_pri_contact_first_nm;
    String merch_pri_contact_last_nm;
    String merch_pri_contact_email;
    String merch_pri_contact_phone;
    String merch_sec_contact_first_nm;
    String merch_sec_contact_last_nm;
    String merch_sec_contact_email;
    String merch_sec_contact_phone;
    LocalDateTime merch_activation_dtm;
    String merch_transact_pri_currency_cd;
    String merch_transact_sec_currency_cd;
    String merch_is_deleted_flg;
    List<ProductMerchantSubscription> productMerchantSubscriptions;
}