package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.AddressDto;
import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.entity.Affiliate;
import com.trnd.trndapi.events.AffiliateCreatedEvent;
import com.trnd.trndapi.exception.AffiliateNoFoundException;
import com.trnd.trndapi.mapper.AddressMapper;
import com.trnd.trndapi.mapper.AffiliateMapper;
import com.trnd.trndapi.repository.AffiliateRepository;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AffiliateServiceImpl implements AffiliateService{

    private final AffiliateRepository affiliateRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AffiliateMapper affiliateMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;

    /**
     * @param id
     * @return
     */
    @Override
    public AffiliateDto viewAffiliate(Long id) {
        Affiliate affiliate = affiliateRepository.findById(id).orElseThrow(() -> new AffiliateNoFoundException("Error: Affiliate not found"));
        return affiliateMapper.toDto(affiliate);
    }

    /**
     * @param affiliateDto
     * @return
     */
    @Override
    public ResponseDto updateAffiliate(AffiliateDto affiliateDto) {
        Affiliate affiliate = affiliateRepository.findById(affiliateDto.getAffId()).orElseThrow(() -> new AffiliateNoFoundException("Error: Affiliate not found"));
        if(Objects.nonNull(affiliateDto.getAddressDto())){
            AddressDto addressDto = addressService.findByIdAndStateCodeAndCityAndZipcodeAndTimezone(affiliateDto.getAddressDto());
            if(Objects.isNull(addressDto))
                return ResponseDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .statusCode(HttpStatus.BAD_REQUEST.toString())
                        .statusMsg("ERROR ADDRESS NOT FOUND")
                        .build();

            affiliate.setAddress(addressMapper.toEntity(addressDto));

        }
        affiliate.setAffFirstNm(affiliateDto.getAffFirstNm());
        affiliate.setAffLastNm(affiliate.getAffLastNm());
        affiliate.setAffIsDeletedFlg(Boolean.FALSE.toString());
        affiliate.setAffAddrLn1(affiliateDto.getAffAddrLn1());
        affiliate.setAffAddrLn2(affiliateDto.getAffAddrLn2());
        affiliate.setAffPayoutPriCurrencyCd(affiliateDto.getAffPayoutPriCurrencyCd());
        affiliate.setAffPayoutSecCurrencyCd(affiliateDto.getAffPayoutSecCurrencyCd());

        Affiliate saveAffiliate = affiliateRepository.save(affiliate);
        return ResponseDto.builder()
                .code(HttpStatus.OK.value())
                .statusCode(HttpStatus.OK.toString())
                .statusMsg("RECORD UPDATED SUCCESSFULLY")
                .data(affiliateMapper.toDto(saveAffiliate))
                .build();
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
        Affiliate saveAffiliate = affiliateRepository.save(affiliateMapper.toEntity(affiliateDto));
        AffiliateDto savedAffiliateDto = affiliateMapper.toDto(saveAffiliate);
        /** Trigger affiliateCreatedEvent, send Email notification that users' profile is created and onboarding approval from merchant is pending.
         will be notified as the review & approval process is completed.
         */
        AffiliateCreatedEvent affiliateCreatedEvent = new AffiliateCreatedEvent(savedAffiliateDto);
        applicationEventPublisher.publishEvent(affiliateCreatedEvent);

        return savedAffiliateDto;
    }

    /**
     * @param affiliateDto
     * @param merchantDto
     * @return
     */
    @Override
    public AffiliateDto createAffiliate(AffiliateDto affiliateDto, MerchantDto merchantDto) {
        Affiliate saveAffiliate = affiliateRepository.save(affiliateMapper.toEntity(affiliateDto));
        AffiliateDto savedAffiliateDto = affiliateMapper.toDto(saveAffiliate);
        /** Trigger affiliateCreatedEvent, send Email notification that users' profile is created and onboarding approval from merchant is pending.
         will be notified as the review & approval process is completed.
         */
        AffiliateCreatedEvent affiliateCreatedEvent = new AffiliateCreatedEvent(savedAffiliateDto,merchantDto);
        applicationEventPublisher.publishEvent(affiliateCreatedEvent);

        return savedAffiliateDto;
    }

    /**
     * @return
     */
    @Override
    public ResponseDto viewAllAffiliate() {
        List<Affiliate> affiliates = affiliateRepository.findAll();
        ResponseDto<?> responseDto = null;
        if(affiliates.isEmpty()){
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("RECORD NOT FOUND")
                    .build();
        }else{
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg("RECORD FOUND")
                    .data(affiliateMapper.toDtoList(affiliates))
                    .build();
        }
        return responseDto;
    }

    /**
     * @return
     */
    @Override
    public ResponseDto viewProfile() {
        String email = SecurityUtils.getLoggedInUserName();
        Optional<Affiliate> affiliate = affiliateRepository.findByEmail(email);
        ResponseDto<?> responseDto = null;
        if(affiliate.isEmpty()){
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("RECORD NOT FOUND")
                    .build();
        }else{
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg("RECORD FOUND")
                    .data(affiliateMapper.toDto(affiliate.orElseThrow(()-> new AffiliateNoFoundException("ERROR: Affiliate not found : "+email))))
                    .build();
        }
        return responseDto;
    }
}
