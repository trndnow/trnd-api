package com.trnd.trndapi.merchant.service;

import com.trnd.trndapi.merchant.dto.MerchantDto;
import com.trnd.trndapi.security.playload.response.MessageResponse;
import org.apache.logging.log4j.message.Message;

public interface MerchantService {

    MerchantDto createMerchant(MerchantDto merchantDto);
    MerchantDto viewMerchant(String merch_nm);
    MerchantDto viewMerchant(long id);
    MerchantDto updateMerchant(MerchantDto merchantDto);
    MessageResponse deleteAccount(String email);

}
