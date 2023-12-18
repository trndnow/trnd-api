package com.trnd.trndapi.security.service;

public interface SignatureVerificationService {
    boolean verifySignature(String data, String signature);
}
