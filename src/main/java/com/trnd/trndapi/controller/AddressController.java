package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.AddressDto;
import com.trnd.trndapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/address")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/view_all")
    public ResponseEntity<?> viewAllBusinessServiceCategory(){
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @GetMapping("/search")
    public List<AddressDto> searchAddresses(@RequestParam String term) {
        return addressService.searchAddresses(term);
    }

}
