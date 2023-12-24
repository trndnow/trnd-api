package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.DashboardStatistics;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    DashboardStatistics getDashboardStatistics();
}
