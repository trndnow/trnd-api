package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    public ResponseEntity<?> addCampaign(@Valid @RequestBody CampaignDto campaignDto){
        return ResponseEntity.ok(campaignService.addCampaign(campaignDto));
    }
    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    @GetMapping("/view/{campaignId}")
    public ResponseEntity<?> viewCampaign(@PathVariable long campaignId){
        return new ResponseEntity<>(campaignService.viewCampaign(campaignId), HttpStatus.OK);
    }

    @GetMapping("/viewAllCampaign")
    public ResponseEntity<?> viewAllCampaign(){
        return new ResponseEntity<>(campaignService.viewAllCampaign(),HttpStatus.OK);
    }

    @GetMapping("/view/byMerchantCode/{merchantCode}")
    public ResponseEntity<?> viewCampaignByMerchantCode(@PathVariable String merchantCode){
        return new ResponseEntity<>(campaignService.viewCampaignByMerchantCode(merchantCode),HttpStatus.OK);
    }

}
