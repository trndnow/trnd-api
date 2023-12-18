package com.trnd.trndapi.service;

import com.trnd.trndapi.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface ProfileCompletenessCalculator {
    int calculateProfileCompleteness(User user);
}
