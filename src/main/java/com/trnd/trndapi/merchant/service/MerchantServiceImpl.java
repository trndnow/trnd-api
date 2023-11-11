package com.trnd.trndapi.merchant.service;

import com.trnd.trndapi.commons.mapper.AddressCityRefMapper;
import com.trnd.trndapi.commons.mapper.BusinessServiceCategoryRefMapper;
import com.trnd.trndapi.commons.mapper.TimezoneRefMapper;
import com.trnd.trndapi.commons.util.DateTimeUtils;
import com.trnd.trndapi.merchant.dto.MerchantDto;
import com.trnd.trndapi.merchant.entity.Merchant;
import com.trnd.trndapi.merchant.exception.MerchantNoFoundException;
import com.trnd.trndapi.merchant.mapper.MerchantMapper;
import com.trnd.trndapi.merchant.repositoty.MerchantRepository;
import com.trnd.trndapi.security.playload.response.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService{
    private MerchantRepository merchantRepository;

    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public MerchantDto createMerchant(MerchantDto merchantDto) {
        return MerchantMapper.INSTANCE.toDto(merchantRepository.save(MerchantMapper.INSTANCE.toEntity(merchantDto)));
    }

    @Override
    public MerchantDto viewMerchant(String merchName) {
        return null;
    }

    @Override
    public MerchantDto viewMerchant(long id) {
        return  MerchantMapper.INSTANCE.toDto(merchantRepository.findById(id).orElseThrow(() -> new MerchantNoFoundException("Error: Merchant not found")));
    }

    @Override
    public MerchantDto updateMerchant(MerchantDto merchantDto) {
        Merchant merchant = merchantRepository.findById(merchantDto.getMerch_id()).orElseThrow(() -> new MerchantNoFoundException("Error: Merchant not found"));
        merchant.setMerch_nm(merchantDto.getMerch_nm());
        merchant.setMerch_descr(merchantDto.getMerch_descr());
        merchant.setBus_svc_cat_id(BusinessServiceCategoryRefMapper.INSTANCE.toEntity(merchantDto.getBus_svc_cat_id()));
        merchant.setMerch_status(merchant.getMerch_status());
        merchant.setLogo_url(merchantDto.getLogo_url()); //TODO:Logo will be uploaded to S3 bucket.
        //TODO: Generate QR Code. What info to include in QR code
        //TODO: Generate the unique link
        merchant.setMerch_addr_in1(merchantDto.getMerch_addr_in1());
        merchant.setMerch_addr_in2(merchantDto.getMerch_addr_in2());
        merchant.setAddr_city_id(AddressCityRefMapper.INSTANCE.convertToEntity(merchantDto.getAddr_city_id()));
        merchant.setMerch_addr_zip(merchantDto.getMerch_addr_zip());
        merchant.setTimezone_id(TimezoneRefMapper.INSTANCE.convertToEntity(merchantDto.getTimezone_id()));
        merchant.setMerch_pri_contact_first_nm(merchantDto.getMerch_pri_contact_first_nm());
        merchant.setMerch_pri_contact_last_nm(merchant.getMerch_pri_contact_last_nm());
        /*INFO: merch_pri_contact_email  &  merch_pri_contact_phone is already set while registration and cannot be changed.*/
        merchant.setMerch_sec_contact_first_nm(merchant.getMerch_sec_contact_first_nm());
        merchant.setMerch_sec_contact_last_nm(merchant.getMerch_sec_contact_last_nm());
        merchant.setMerch_sec_contact_email(merchant.getMerch_sec_contact_email());
        merchant.setMerch_sec_contact_phone(merchant.getMerch_sec_contact_phone());
        /*INFO: merch_activation_dtm will be set when the activation is approved*/
        merchant.setMerch_transact_pri_currency_cd(merchant.getMerch_transact_pri_currency_cd());
        merchant.setMerch_transact_sec_currency_cd(merchant.getMerch_transact_sec_currency_cd());
        merchant.setMerch_is_deleted_flg(Boolean.FALSE.toString());
        //TODO: ProductMerchantSubscription is not set

        Merchant savedMerchant = merchantRepository.save(merchant);

        return MerchantMapper.INSTANCE.toDto(savedMerchant);
    }

    @Override
    public MessageResponse deleteAccount(String email) {
        Merchant merchant = merchantRepository.findByEmail(email).orElseThrow(() -> new MerchantNoFoundException("ERROR: Merchant not found"));
        merchant.setMerch_delete_account_dt(DateTimeUtils.getDateTimeAfterThirtyDaysAtElevenPM());
        merchant.setMerch_is_deleted_flg(Boolean.TRUE.toString());
        merchantRepository.save(merchant);
        //TODO: Trigger DELETE_ACCOUNT_EVENT for invalidating the password in the User table.
        //TODO: Write a batch job to delete the account after 30 days at 11:00PM
        //TODO: Disable the account
        return MessageResponse.builder()
                .message("YOUR ACCOUNT WILL BE PERMANENTLY DELETED AFTER 30 DAYS").build();
    }
}
