package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.entity.Affiliate;
import com.trnd.trndapi.events.AffiliateCreatedEvent;
import com.trnd.trndapi.exception.AffiliateNoFoundException;
import com.trnd.trndapi.mapper.AddressMapper;
import com.trnd.trndapi.mapper.AffiliateMapper;
import com.trnd.trndapi.repository.AffiliateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AffiliateServiceImpl implements AffiliateService{

    private final AffiliateRepository affiliateRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * @param id
     * @return
     */
    @Override
    public AffiliateDto viewAffiliate(Long id) {
        Affiliate affiliate = affiliateRepository.findById(id).orElseThrow(() -> new AffiliateNoFoundException("Error: Affiliate not found"));
        return AffiliateMapper.INSTANCE.toDto(affiliate);
    }

    /**
     * @param affiliateDto
     * @return
     */
    @Override
    public AffiliateDto updateAffiliate(AffiliateDto affiliateDto) {
        Affiliate affiliate = affiliateRepository.findById(affiliateDto.getAffId()).orElseThrow(() -> new AffiliateNoFoundException("Error: Affiliate not found"));
        affiliate.setAffFirstNm(affiliateDto.getAffFirstNm());
        affiliate.setAffLastNm(affiliate.getAffLastNm());
        affiliate.setAffIsDeletedFlg(Boolean.FALSE.toString());
        affiliate.setAffAddrLn1(affiliateDto.getAffAddrLn1());
        affiliate.setAffAddrLn2(affiliateDto.getAffAddrLn2());
        affiliate.setAddrId(AddressMapper.INSTANCE.toEntity(affiliateDto.getAddrId()));
        affiliate.setAffPayoutPriCurrencyCd(affiliateDto.getAffPayoutPriCurrencyCd());
        affiliate.setAffPayoutSecCurrencyCd(affiliateDto.getAffPayoutSecCurrencyCd());

        Affiliate saveAffiliate = affiliateRepository.save(affiliate);
        return AffiliateMapper.INSTANCE.toDto(saveAffiliate);
    }

    /**
     * @param email
     */
    @Override
    public void deleteAccount(String email) {

    }

    /**
     * @param affiliateDto
     * @return
     */
    @Override
    public AffiliateDto createAffiliate(AffiliateDto affiliateDto) {
        Affiliate saveAffiliate = affiliateRepository.save(AffiliateMapper.INSTANCE.toEntity(affiliateDto));
        AffiliateDto savedAffiliateDto = AffiliateMapper.INSTANCE.toDto(saveAffiliate);
        /** Trigger affiliateCreatedEvent, send Email notification that users' profile is created and onboarding approval from merchant is pending.
         will be notified as the review & approval process is completed.
         */
        AffiliateCreatedEvent affiliateCreatedEvent = new AffiliateCreatedEvent(savedAffiliateDto);
        applicationEventPublisher.publishEvent(affiliateCreatedEvent);

        return savedAffiliateDto;
    }
}
