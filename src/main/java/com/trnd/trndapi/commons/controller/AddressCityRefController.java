package com.trnd.trndapi.commons.controller;

import com.trnd.trndapi.commons.dto.AddressCityRefDto;
import com.trnd.trndapi.commons.service.AddressCityRefService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/city")
@Slf4j
@RequiredArgsConstructor
public class AddressCityRefController {

    private final AddressCityRefService addressCityRefService;

    @PostMapping("/add")
    public ResponseEntity<?> addCity(@Valid @RequestBody AddressCityRefDto addressCityRefDto){
        return  ResponseEntity.ok(addressCityRefService.addCity(addressCityRefDto));
    }

}
