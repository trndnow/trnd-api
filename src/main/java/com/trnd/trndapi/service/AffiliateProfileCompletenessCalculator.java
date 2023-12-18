package com.trnd.trndapi.service;

import com.trnd.trndapi.entity.Affiliate;
import com.trnd.trndapi.entity.Merchant;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.exception.AffiliateNoFoundException;
import com.trnd.trndapi.exception.MerchantNoFoundException;
import com.trnd.trndapi.repository.AffiliateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Slf4j
@Service
@RequiredArgsConstructor
public class AffiliateProfileCompletenessCalculator implements ProfileCompletenessCalculator{
    private final AffiliateRepository affiliateRepository;

    /**
     * @param user
     * @return
     */
    @Override
    public int calculateProfileCompleteness(User user) {
        Affiliate affiliate = affiliateRepository.findByEmail(user.getEmail()).orElseThrow(()-> new AffiliateNoFoundException("Error: Affiliate not found"));
        Field[] fields = Affiliate.class.getDeclaredFields();
        int totalFields = fields.length; // Total number of fields considered for profile completeness
        int filledFields = 0;

        for (Field field : fields) {
            field.setAccessible(true); // Make the field accessible if it's private
            try {
                Object value = field.get(affiliate); // Get the value of the field for the merchant object
                if (value != null) {
                    filledFields++; // Increment if the field is not null
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }
        return (filledFields * 100) / totalFields;
    }
}
