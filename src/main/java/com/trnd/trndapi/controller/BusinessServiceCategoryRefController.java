package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.BusinessServiceCategoryRefDto;
import com.trnd.trndapi.service.BusinessServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/bus_svc_cat")
@Slf4j
@RequiredArgsConstructor
public class BusinessServiceCategoryRefController {

    private final BusinessServiceCategoryService businessServiceCategoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addBusinessServiceCategory(@Valid @RequestBody BusinessServiceCategoryRefDto businessServiceCategoryRefDto){
        return  ResponseEntity.ok(businessServiceCategoryService.addBusinessServiceCategory(businessServiceCategoryRefDto));
    }

    @GetMapping("/view_all")
    public ResponseEntity<?> viewAllBusinessServiceCategory(){
        return ResponseEntity.ok(businessServiceCategoryService.viewAllBusinessServiceCategory());
    }

}
