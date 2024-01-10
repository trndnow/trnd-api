package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import com.trnd.trndapi.security.playload.response.MessageResponse;
import com.trnd.trndapi.service.MerchantService;
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
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    public ResponseEntity<?> createMerchant(@Valid @RequestBody MerchantDto merchantDto){
        /**The all the user are crated via when user register via userCreatedEvent,so it will be only have update*/
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> viewMerchant(@PathVariable Long id){
        return ResponseEntity.ok(merchantService.viewMerchant(id));
    }

    @GetMapping("/view_profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<?> viewProfile(){
        return ResponseEntity.ok(merchantService.viewProfile());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> viewAllMerchant(){
        return ResponseEntity.ok(merchantService.viewAllMerchant());
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> updateMerchant(@RequestBody MerchantDto merchantDto){
        if(SecurityUtils.hasRole(ERole.ROLE_MERCHANT.name())){
            log.info("LOGGED IN USER IS MERCHANT");
            String loggedInUserName = SecurityUtils.getLoggedInUserName();
            if(merchantDto.getMerchPriContactEmail().equals(loggedInUserName))
                return ResponseEntity.badRequest().body(MessageResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.toString())
                        .message("Action not permitted")
                        .build()
                );

        }
        return ResponseEntity.ok(merchantService.updateMerchant(merchantDto));
    }

    @GetMapping("/delete-account")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> deleteAccount(){
        String email = SecurityUtils.getLoggedInUserName();
        merchantService.deleteAccount(email);
        Collection<String> loggedInUserRoles = SecurityUtils.getLoggedInUserRoles();
        log.info("LOGGED IN USER : {} ", email);
        return ResponseEntity.ofNullable(HttpStatus.METHOD_NOT_ALLOWED);
    }


}
