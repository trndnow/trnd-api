package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.ResponseDto;

/**
 *
 */
public interface AffiliateService {
    AffiliateDto viewAffiliate(Long id);

    ResponseDto updateAffiliate(AffiliateDto affiliateDto);

    void deleteAccount(String email);

    AffiliateDto createAffiliate(AffiliateDto affiliateDto);
    AffiliateDto createAffiliate(AffiliateDto affiliateDto, MerchantDto merchantDto);

    ResponseDto viewAllAffiliate();

    ResponseDto viewProfile();
}
