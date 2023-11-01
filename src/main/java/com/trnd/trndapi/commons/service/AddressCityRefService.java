package com.trnd.trndapi.commons.service;

import com.trnd.trndapi.commons.dto.AddressCityRefDto;
import org.springframework.stereotype.Service;

@Service
public interface AddressCityRefService {
    AddressCityRefDto addCity(AddressCityRefDto addressCityRefDto);
}
