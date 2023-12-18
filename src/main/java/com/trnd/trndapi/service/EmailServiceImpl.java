package com.trnd.trndapi.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private static final String NO_REPLY_ADDRESS = "noreply@trndnow.com";

    private final JavaMailSender emailSender;

    private final SimpleMailMessage template;

    private final OtpService otpService;
    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("classpath:/mail-logo.png")
    private Resource resourceFile;

    /**
     * @param to
     * @param subject
     * @param templateModel
     * @param templateName
     * @throws MessagingException
     */
    @Override
    public void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel, String templateName) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);

        String htmlBody = thymeleafTemplateEngine.process(templateName, thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NO_REPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("attachment.png", resourceFile);
        emailSender.send(message);
    }
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NO_REPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException NO_REPLY) {
           log.error(NO_REPLY.getLocalizedMessage());
        }
    }

    /**
     * @param to
     * @param subject
     * @param templateModel
     */
    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel) {

    }

    /**
     * @param to
     * @param subject
     * @param text
     * @param pathToAttachment
     */
    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {

    }

    /**
     * @param to
     * @param subject
     * @param templateModel
     */
    @Override
    public void sendOtpEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        sendMessageUsingThymeleafTemplate(to,subject,templateModel,"otp-email-template.html");
    }

    /**
     * @param to
     * @param subject
     * @param templateModel
     */
    @Override
    public void sendOnboardingEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        sendMessageUsingThymeleafTemplate(to,subject,templateModel,"onboarding-email-template.html");
    }

    @Override
    public void sendPendingApprovalEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        sendMessageUsingThymeleafTemplate(to,subject,templateModel,"onboarding-email-template.html");
    }

}
