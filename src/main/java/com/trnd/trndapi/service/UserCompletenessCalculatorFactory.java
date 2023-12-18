package com.trnd.trndapi.service;

import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.ERole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCompletenessCalculatorFactory implements ProfileCompletenessCalculatorFactory{
    @Autowired
    private MerchantProfileCompletenessCalculator merchantProfileCompletenessCalculator;

    @Autowired
    private AffiliateProfileCompletenessCalculator affiliateProfileCompletenessCalculator;
    /**
     * @param user
     * @return
     */
    @Override
    public ProfileCompletenessCalculator createCalculator(User user) {
        log.debug("createCalculator");
        if(user.getRole().getName() != null && user.getRole().getName().equals(ERole.ROLE_MERCHANT)){
            return merchantProfileCompletenessCalculator;
        }else if(user.getRole().getName() != null  && user.getRole().getName().equals(ERole.ROLE_AFFILIATE)){
            return affiliateProfileCompletenessCalculator;
        }else{
            throw new IllegalArgumentException("Unknown user type");
        }
    }
}
