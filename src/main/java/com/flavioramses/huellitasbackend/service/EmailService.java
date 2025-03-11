package com.flavioramses.huellitasbackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendRegistrationConfirmation(String toEmail, String username) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("Hotel Huellitas <f0040859@gmail.com>");

        helper.setTo(toEmail);
        helper.setSubject("Confirmación de Registro");

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("email", toEmail);

        String htmlContent = templateEngine.process("emailMessage", context);


        helper.setText(htmlContent, true);
        mailSender.send(message);

    }
}