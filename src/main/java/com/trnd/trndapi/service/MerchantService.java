package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.exception.MerchantNoFoundException;
import com.trnd.trndapi.security.playload.response.MessageResponse;

public interface MerchantService {

    MerchantDto createMerchant(MerchantDto merchantDto);
    MerchantDto viewMerchant(String merch_nm);

    ResponseDto viewMerchant(long id);
    ResponseDto updateMerchant(MerchantDto merchantDto);
    MessageResponse deleteAccount(String email);
    MerchantDto getMerchantByID(MerchantDto merchId) throws MerchantNoFoundException;

    MerchantDto getMerchantByMerchantUniqueCode(String userCode);

    MerchantDto getMerchantByMerchantCode(String merchantCode);

    ResponseDto viewProfile();

    ResponseDto viewAllMerchant();
}
