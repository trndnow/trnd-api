package com.trnd.trndapi.security.service;

import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

//@Service
//public class SignatureVerificationServiceImpl implements SignatureVerificationService{
//    private final PublicKey publicKey;
//
//    public SignatureVerificationServiceImpl(PublicKey publicKey) {
//        this.publicKey = publicKey;
//    }
//
//    /**
//     * @param data
//     * @param signature
//     * @return
//     */
//    @Override
//    public boolean verifySignature(String data, String signature) {
//        try {
//            Signature sig = Signature.getInstance("SHA256withRSA");
//            sig.initVerify(publicKey);
//            sig.update(data.getBytes());
//
//            // Assuming the signature is base64 encoded
//            byte[] signatureBytes = Base64.getDecoder().decode(signature);
//            return sig.verify(signatureBytes);
//        } catch (Exception e) {
//            // Log exception
//            return false;
//        }
//    }
//}
