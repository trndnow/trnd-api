package com.trnd.trndapi.merchant.service;

import com.trnd.trndapi.merchant.dto.MerchantDto;

public interface MerchantService {

    MerchantDto createMerchant(MerchantDto merchantDto);
    MerchantDto viewMerchant(String merch_nm);
    MerchantDto viewMerchant(long id);
    MerchantDto updateMerchant(MerchantDto merchantDto);
}
