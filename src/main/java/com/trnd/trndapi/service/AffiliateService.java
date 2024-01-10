package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.dto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AffiliateService {
    AffiliateDto viewAffiliate(Long id);

    AffiliateDto updateAffiliate(AffiliateDto affiliateDto);

    void deleteAccount(String email);

    AffiliateDto createAffiliate(AffiliateDto affiliateDto);

    ResponseDto viewAllAffiliate();

    ResponseDto viewProfile();
}
