package com.trnd.trndapi.security;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;

public class SignatureTestMain {

    public static void main(String[] args) throws Exception {
        String json = "{\"email\": \"test1@gmail.com\",\"password\": \"ambrish@123#\"}"; // Your JSON data
        byte[] jsonData = json.getBytes();

        String keystorePath = "D:\\trnd\\trnd-api\\src\\main\\resources\\trndkeystore.jks";
        String alias = "trndkey";
        String keystorePassword = "trnd@99!";
        String keyPassword = "trnd@99!";

        PrivateKey privateKey = getPrivateKeyFromKeystore(keystorePath, alias, keystorePassword, keyPassword);
        byte[] signature = signData(jsonData, privateKey);

        // Convert signature to Base64 for easy transmission
        String base64Signature = java.util.Base64.getEncoder().encodeToString(signature);

        // Output the Base64 signature
        System.out.println("Signature: " + base64Signature);
    }

    public static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static PrivateKey getPrivateKeyFromKeystore(String keystorePath, String alias, String keystorePassword, String keyPassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            keyStore.load(fis, keystorePassword.toCharArray());
        }
        return (PrivateKey) keyStore.getKey(alias, keyPassword.toCharArray());
    }

}
