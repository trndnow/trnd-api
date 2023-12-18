package com.trnd.trndapi.service;

import com.trnd.trndapi.entity.Merchant;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.exception.MerchantNoFoundException;
import com.trnd.trndapi.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantProfileCompletenessCalculator implements ProfileCompletenessCalculator{

    private final MerchantRepository merchantRepository;

    /**
     * @param user
     * @return
     */
    @Override
    public int calculateProfileCompleteness(User user) {
        Merchant merchant = merchantRepository.findByEmail(user.getEmail()).orElseThrow(()-> new MerchantNoFoundException("Error: Merchant not found"));
        Field[] fields = Merchant.class.getDeclaredFields();
        int totalFields = fields.length; // Total number of fields considered for profile completeness
        int filledFields = 0;

        for (Field field : fields) {
            field.setAccessible(true); // Make the field accessible if it's private
            try {
                Object value = field.get(merchant); // Get the value of the field for the merchant object
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
