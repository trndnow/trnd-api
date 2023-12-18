package com.trnd.trndapi.service;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
    void sendSimpleMessageUsingTemplate(String to, String subject, String ...templateModel);
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
    void sendOtpEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException;
    void sendOnboardingEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException;
    void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,String templateName)
            throws MessagingException;

    void sendPendingApprovalEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException;
}
