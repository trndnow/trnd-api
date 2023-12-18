package com.trnd.trndapi.service;

import com.trnd.trndapi.entity.User;

public interface ProfileCompletenessCalculatorFactory {
    ProfileCompletenessCalculator createCalculator(User user);
}
