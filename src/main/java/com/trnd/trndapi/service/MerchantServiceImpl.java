package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.entity.Merchant;
import com.trnd.trndapi.events.MerchantCreatedEvent;
import com.trnd.trndapi.exception.MerchantNoFoundException;
import com.trnd.trndapi.mapper.AddressMapper;
import com.trnd.trndapi.mapper.BusinessServiceCategoryRefMapper;
import com.trnd.trndapi.mapper.MerchantMapper;
import com.trnd.trndapi.repository.MerchantRepository;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import com.trnd.trndapi.security.playload.response.MessageResponse;
import com.trnd.trndapi.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService{
    private final MerchantRepository merchantRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MerchantMapper merchantMapper;
    private final BusinessServiceCategoryRefMapper businessServiceCategoryRefMapper;
    private final AddressMapper addressMapper;

    @Override
    public MerchantDto createMerchant(MerchantDto merchantDto) {
        Merchant merchant = merchantMapper.toEntity(merchantDto);
        Merchant savedMerchant = merchantRepository.save(merchant);
        MerchantDto createdMerchantDto = merchantMapper.toDto(savedMerchant);
        /** Trigger merchantCreatedEvent, send Email notification that users' profile is created and onboarding approval from trndnow is pending.
            will be notified as the review & approval process is completed.
        */
        MerchantCreatedEvent merchantCreatedEvent = new MerchantCreatedEvent(createdMerchantDto);
        applicationEventPublisher.publishEvent(merchantCreatedEvent);
        return createdMerchantDto;

    }

    @Override
    public MerchantDto viewMerchant(String merchName) {
        return null;
    }

    @Override
    public ResponseDto viewMerchant(long id) {
        Optional<Merchant> merchantOptional = merchantRepository.findById(id);
        ResponseDto<?> responseDto = null;
        if(merchantOptional.isEmpty()){
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("RECORD NOT FOUND")
                    .build();

        }else{
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg("RECORD FOUND")
                    .data(merchantMapper.toDto(merchantOptional.orElseThrow(() -> new MerchantNoFoundException("ERROR: Merchant not found : "+id))))
                    .build();
        }
        return  responseDto;
    }

    @Override
    public ResponseDto updateMerchant(MerchantDto merchantDto) {
        Merchant merchant = merchantRepository.findById(merchantDto.getMerchId()).orElseThrow(() -> new MerchantNoFoundException("Error: Merchant not found"));
         /** Merchant name is set at the time of registration */
        merchant.setMerchDescr(merchantDto.getMerchDescr());
        merchant.setBusinessServiceCategoryRef(businessServiceCategoryRefMapper.toEntity(merchantDto.getBusinessServiceCategoryRefDto()));
        merchant.setMerchStatus(merchantDto.getMerchStatus());
        merchant.setLogoUrl(merchantDto.getLogoUrl()); //TODO:Logo will be uploaded to S3 bucket.
        //TODO: Generate QR Code. QR Code will have the unique link
        //TODO: Generate the unique link EX: trndnow.com/merchant_code 4906309a-54cd-457e-9a65-835156741485 - should be reduce to max 6 digit
        merchant.setMerchAddrIn1(merchantDto.getMerchAddrIn1());
        merchant.setMerchAddrIn2(merchantDto.getMerchAddrIn2());
        merchant.setAddress(addressMapper.toEntity(merchantDto.getAddressDto()));
        merchant.setMerchPriContactFirstNm(merchantDto.getMerchPriContactFirstNm());
        merchant.setMerchPriContactLastNm(merchantDto.getMerchPriContactLastNm());
        /**INFO: merch_pri_contact_email  &  merch_pri_contact_phone is already set while registration and cannot be changed.*/
        merchant.setMerchSecContactFirstNn(merchantDto.getMerchSecContactFirstNn());
        merchant.setMerchSecContactLastNm(merchantDto.getMerchSecContactLastNm());
        merchant.setMerchSecContactEmail(merchantDto.getMerchSecContactEmail());
        merchant.setMerchSecContactPhone(merchantDto.getMerchSecContactPhone());
        /**INFO: merch_activation_dtm will be set when the activation is approved*/
        merchant.setMerchTransactPriCurrencyCd(merchantDto.getMerchTransactPriCurrencyCd());
        merchant.setMerchTransactSecCurrencyCd(merchantDto.getMerchTransactSecCurrencyCd());
        merchant.setMerchIsDeletedFlg(Boolean.FALSE.toString());
        Merchant savedMerchant = merchantRepository.save(merchant);

        return ResponseDto.builder()
                .statusCode(HttpStatus.OK.toString())
                .statusMsg("RECORD UPDATED SUCCESSFULLY")
                .data(merchantMapper.toDto(savedMerchant))
                .build();
    }

    @Override
    public MessageResponse deleteAccount(String email) {
        Merchant merchant = merchantRepository.findByEmail(email).orElseThrow(() -> new MerchantNoFoundException("ERROR: Merchant not found"));
        merchant.setMerchDeleteAccountDt(DateTimeUtils.getDateTimeAfterThirtyDaysAtElevenPM());
        merchant.setMerchIsDeletedFlg(Boolean.TRUE.toString());
        merchantRepository.save(merchant);
        //TODO: Trigger DELETE_ACCOUNT_EVENT for invalidating the password in the User table.
        //TODO: Write a batch job to delete the account after 30 days at 11:00PM
        //TODO: Disable the account
        return MessageResponse.builder()
                .message("YOUR ACCOUNT WILL BE PERMANENTLY DELETED AFTER 30 DAYS").build();
    }

    /**
     * @param merchantDto
     * @return merchantDto
     */
    @Override
    public MerchantDto getMerchantByID(MerchantDto merchantDto) throws MerchantNoFoundException{
        return merchantMapper.toDto(merchantRepository.findById(merchantDto.getMerchId()).orElseThrow(()->new MerchantNoFoundException("ERROR:MERCHANT NOT FOUND "+ merchantDto.getMerchId())));
    }

    /**
     * @param userCode
     * @return
     */
    @Override
    public MerchantDto getMerchantByMerchantUniqueCode(String userCode) {
        Optional<Merchant> byMerchantUniqueCode = merchantRepository.findByMerchantUniqueCode(userCode);
        if(byMerchantUniqueCode.isPresent())
            return merchantMapper.toDto(byMerchantUniqueCode.get());
        return null;
    }

    /**
     * @param merchantCode
     * @return
     */
    @Override
    public MerchantDto getMerchantByMerchantCode(String merchantCode) {
        Optional<Merchant> byMerchantCode = merchantRepository.findByMerchantCode(merchantCode);
        if(byMerchantCode.isPresent())
            return merchantMapper.toDto(byMerchantCode.get());
       throw new MerchantNoFoundException("Error : Merchant Code not found");
    }

    /**
     * @return
     */
    @Override
    public ResponseDto viewProfile() {
        String email = SecurityUtils.getLoggedInUserName();
        Optional<Merchant> merchant = merchantRepository.findByEmail(email);
        ResponseDto<?> responseDto = null;
        if(merchant.isEmpty()){
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("RECORD NOT FOUND")
                    .build();
        }else{
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg("RECORD FOUND")
                    .data(merchantMapper.toDto(merchant.orElseThrow(()-> new MerchantNoFoundException("ERROR: Merchant not found : "+email))))
                    .build();
        }
        return responseDto;
    }

    /**
     * @return
     */
    @Override
    public ResponseDto viewAllMerchant() {
        List<Merchant> merchant = merchantRepository.findAll();
        ResponseDto<?> responseDto = null;
        if(merchant.isEmpty()){
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("RECORD NOT FOUND")
                    .build();
        }else{
            responseDto = ResponseDto.builder()
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg("RECORD FOUND")
                    .data(merchantMapper.toDtoList(merchant))
                    .build();
        }
        return responseDto;
    }
}
