package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.service.MerchantService;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    public ResponseEntity<?> createMerchant(@Valid @RequestBody MerchantDto merchantDto){
        /**The all the user are crated via when user register via userCreatedEvent,so it will be only have update*/
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);

    }

    @GetMapping("/{id}")
    private ResponseEntity<?> viewMerchant(@PathVariable Long id){
        return ResponseEntity.ok(merchantService.viewMerchant(id));

    }

    @PostMapping("/update")
    private ResponseEntity<?> updateMerchant(@RequestBody MerchantDto merchantDto){
        log.info("Update Merchant");
        return ResponseEntity.ok(merchantService.updateMerchant(merchantDto));
    }

    @GetMapping("/delete-account")
    private ResponseEntity<?> deleteAccount(){
        String email = SecurityUtils.getLoggedInUserName();
        merchantService.deleteAccount(email);
        Collection<String> loggedInUserRoles = SecurityUtils.getLoggedInUserRoles();
        log.info("LOGGED IN USER : {} ", email);
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);
    }


}
