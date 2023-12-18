package com.trnd.trndapi.utils;

import java.util.UUID;

public class MerchantUtils{

    public static String generateMerchantName(UUID uniqueId){
        String uuidString = uniqueId.toString();
        String[] parts = uuidString.split("-");
        String lastPart = parts[parts.length - 1];
        return lastPart.toUpperCase();
    }
}
