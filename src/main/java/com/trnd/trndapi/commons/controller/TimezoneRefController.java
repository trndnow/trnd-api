package com.trnd.trndapi.commons.controller;

import com.trnd.trndapi.commons.dto.TimezoneRefDto;
import com.trnd.trndapi.commons.service.TimezoneRefService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/timezone")
@Slf4j
@RequiredArgsConstructor
public class TimezoneRefController {

    private final TimezoneRefService timezoneRefService;

    @PostMapping("/add")
    public ResponseEntity<?> addTimezone(@Valid @RequestBody TimezoneRefDto timezoneRefDto){

        return  ResponseEntity.ok(timezoneRefService.addTimezone(timezoneRefDto));
    }

    @PostMapping("/bulk-add")
    public ResponseEntity<?> addTimezone(@Valid @RequestBody List<TimezoneRefDto> timezoneRefDto){
        return  ResponseEntity.ok(timezoneRefService.addTimezone(timezoneRefDto));
    }

    @GetMapping("/view-all")
    public ResponseEntity<?> viewTimezone(){
        return  ResponseEntity.ok(timezoneRefService.viewTimezone());
    }



}
