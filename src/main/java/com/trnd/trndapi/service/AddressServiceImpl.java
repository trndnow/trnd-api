package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.AddressDto;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.entity.Address;
import com.trnd.trndapi.mapper.AddressMapper;
import com.trnd.trndapi.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    /**
     * @return
     */
    @Override
    public ResponseDto getAllAddress() {
        List<Address> addressList = addressRepository.findAll();
        if(addressList.isEmpty()){
            return ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("RECORDS NOT FOUND")
                    .build();
        }else {
            return ResponseDto.builder()
                    .statusCode(HttpStatus.NO_CONTENT.toString())
                    .statusMsg("RECORDS FOUND")
                    .data(addressMapper.toDtoList(addressList))
                    .build();

        }

    }

    /**
     * @param term
     * @return
     */
    @Override
    public List<AddressDto> searchAddresses(String term) {
        // Convert term to an integer if necessary, handle potential NumberFormatException
       int zipcode = convertTermToZipcode(term);
       // addressRepository.findByStateCodeLikeIgnoreCaseOrCityLikeIgnoreCaseOrTimezoneLikeIgnoreCaseOrZipcodeGreaterThanEqual()
        return addressRepository.findByStateCodeContainingIgnoreCaseOrCityContainingIgnoreCaseOrZipcodeGreaterThanEqual(term, term, zipcode)
                .stream().map(addressMapper::toDto).toList();
    }

    private int convertTermToZipcode(String term) {
        try {
            return Integer.parseInt(term);
        } catch (NumberFormatException e) {
            // If the term is not a valid integer, decide what you want to return.
            // You might return a default value, or handle this scenario differently.
            // For example, returning 0 or -1 to indicate an invalid or non-numeric term.
            return 0;
        }
    }
}
