package com.trnd.trndapi.service;

import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.exception.MerchantNoFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ProfileCompletenessCalculator {
    int calculateProfileCompleteness(User user) throws MerchantNoFoundException;
}
