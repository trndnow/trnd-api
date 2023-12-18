package com.trnd.trndapi.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class CommonUtils {


    private static String baseUrl;

    @Value("${application.base.url}")
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static  String generateUniqueCode() {
        return UUID.randomUUID().toString();
    }

    public static String createCampaignUniqueLink(String uniqueCode) {
        String campaignUrl = baseUrl+"/campaign/"+uniqueCode;
        return campaignUrl;
    }

    public static String createMerchantUniqueLink(String uniqueCode) {
        return baseUrl + "/referral/?ref=" + uniqueCode;
    }

    public static byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    public static String generateQRCodeImageAsBase64(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        }
    }

    public static BufferedImage decodeQRCodeImageFromBase64(String base64EncodedImage) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64EncodedImage);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            return ImageIO.read(inputStream);
        }
    }

}
