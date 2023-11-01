package com.trnd.trndapi.commons.service;

import com.trnd.trndapi.commons.mapper.AddressCityRefMapper;
import com.trnd.trndapi.commons.dto.AddressCityRefDto;
import com.trnd.trndapi.commons.repository.AddressCityRefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressCityRefServiceImpl implements AddressCityRefService{

    private final AddressCityRefRepository addressCityRefRepository;
    @Override
    public AddressCityRefDto addCity(AddressCityRefDto addressCityRefDto) {
        return AddressCityRefMapper.INSTANCE.convertToDto(addressCityRefRepository.save(AddressCityRefMapper.INSTANCE.convertToEntity(addressCityRefDto)));
    }
}
