package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.AddressDto;
import com.trnd.trndapi.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    ResponseDto getAllAddress();

    List<AddressDto> searchAddresses(String term);
    AddressDto findByIdAndStateCodeAndCityAndZipcodeAndTimezone(AddressDto addressDto);
}
