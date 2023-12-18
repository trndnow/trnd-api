package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.security.playload.response.MessageResponse;

public interface MerchantService {

    MerchantDto createMerchant(MerchantDto merchantDto);
    MerchantDto viewMerchant(String merch_nm);
    MerchantDto viewMerchant(long id);
    MerchantDto updateMerchant(MerchantDto merchantDto);
    MessageResponse deleteAccount(String email);
    MerchantDto getMerchantByID(MerchantDto merchId);

    MerchantDto getMerchantByMerchantUniqueCode(String userCode);

    MerchantDto getMerchantByMerchantCode(String merchantCode);
}
