package com.trnd.trndapi.merchant.controller;

import com.trnd.trndapi.merchant.dto.MerchantDto;
import com.trnd.trndapi.merchant.service.MerchantService;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    public ResponseEntity<?> createMerchant(@Valid @RequestBody MerchantDto merchantDto){
        //TODO: The all the user are crated via when user register via userCreatedEvent,so it will be only have update
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);

    }


    @GetMapping("/{id}")
    private ResponseEntity<?> viewMerchant(@PathVariable Long id){
        return ResponseEntity.ok(merchantService.viewMerchant(id));

    }

    private ResponseEntity<?> updateMerchant(@Valid @RequestBody MerchantDto merchantDto){
        return ResponseEntity.ok(merchantService.updateMerchant(merchantDto));
    }

    @GetMapping("/delete-account")
    private ResponseEntity<?> deleteAccount(){
        String email = SecurityUtils.getLoggedInUserName();
        merchantService.deleteAccount(email);
        Collection<String> loggedInUserRoles = SecurityUtils.getLoggedInUserRoles();
        log.info("LOGGED IN USER : {} ", email);
        loggedInUserRoles.stream().forEach(s -> {
            System.out.println(s);
        });
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);
    }


}
