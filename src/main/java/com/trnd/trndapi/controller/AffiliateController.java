package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import com.trnd.trndapi.service.AffiliateService;
import com.trnd.trndapi.service.MerchantService;
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
@RequestMapping("/api/v1/affiliate")
@RequiredArgsConstructor
public class AffiliateController {

    private final AffiliateService affiliateService;

    public ResponseEntity<?> createAffiliate(@Valid @RequestBody AffiliateDto affiliateDto){
        /**The all the user are crated via when user register via userCreatedEvent,so it will be only have update*/
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> viewAffiliate(@PathVariable Long id){
        return ResponseEntity.ok(affiliateService.viewAffiliate(id));

    }

    @PostMapping("/update")
    private ResponseEntity<?> updateAffiliate(@RequestBody AffiliateDto affiliateDto){
        return ResponseEntity.ok(affiliateService.updateAffiliate(affiliateDto));
    }

    @GetMapping("/delete-account")
    private ResponseEntity<?> deleteAccount(){
        String email = SecurityUtils.getLoggedInUserName();
        affiliateService.deleteAccount(email);
        Collection<String> loggedInUserRoles = SecurityUtils.getLoggedInUserRoles();
        log.info("LOGGED IN USER : {} ", email);
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
