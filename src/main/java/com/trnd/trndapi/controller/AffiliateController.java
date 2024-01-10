package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.AffiliateDto;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import com.trnd.trndapi.service.AffiliateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    private ResponseEntity<?> viewAffiliate(@PathVariable Long id){
        return ResponseEntity.ok(affiliateService.viewAffiliate(id));

    }

    @GetMapping("/view_profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AFFILIATE')")
    public ResponseEntity<?> viewProfile(){
        return ResponseEntity.ok(affiliateService.viewProfile());
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<?> viewAllAffiliate(){
        return ResponseEntity.ok(affiliateService.viewAllAffiliate());
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AFFILIATE')")
    private ResponseEntity<?> updateAffiliate(@RequestBody AffiliateDto affiliateDto){
        return ResponseEntity.ok(affiliateService.updateAffiliate(affiliateDto));
    }

    @GetMapping("/delete-account")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AFFILIATE')")
    private ResponseEntity<?> deleteAccount(){
        String email = SecurityUtils.getLoggedInUserName();
        affiliateService.deleteAccount(email);
        Collection<String> loggedInUserRoles = SecurityUtils.getLoggedInUserRoles();
        log.info("LOGGED IN USER : {} ", email);
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
