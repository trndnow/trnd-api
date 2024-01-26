package com.trnd.trndapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import com.trnd.trndapi.serializer.View;
import com.trnd.trndapi.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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


    @GetMapping("/basic/all")
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView(View.Basic.class)
    public ResponseEntity<?> getMerchantBasic(){
        List<MerchantDto> merchantBasic = merchantService.getMerchantBasic();
        return ResponseEntity.ok().body(merchantBasic);
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MERCHANT')")
    public ResponseEntity<?> updateMerchant(@RequestBody MerchantDto merchantDto){
        log.info("LOGGED IN USER IS MERCHANT");
        String email = SecurityUtils.getLoggedInUserName();
        if(SecurityUtils.hasRole(ERole.ROLE_MERCHANT.name()) && !merchantDto.getMerchPriContactEmail().equals(email)){
            return ResponseEntity.badRequest().body(ResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.toString())
                    .statusMsg("ACTION NOT PERMITTED: Cannot update other merchants profile")
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
