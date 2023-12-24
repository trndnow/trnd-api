package com.trnd.trndapi.controller;

import com.trnd.trndapi.dto.DashboardStatistics;
import com.trnd.trndapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/statistics")
    public ResponseEntity<?> getDashboardStatistics(){
     DashboardStatistics dashboardStatistics = dashboardService.getDashboardStatistics();
     return ResponseEntity.ok().body(dashboardStatistics);
    }
}
