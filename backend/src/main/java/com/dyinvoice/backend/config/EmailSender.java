package com.dyinvoice.backend.config;


import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Facture;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSender {

    private final JavaMailSender emailSender;
    private SpringTemplateEngine templateEngine;

    @Autowired
    public EmailSender(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }


    public void sendEmail(String from, String to, String subject, String content) throws Exception {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(new InternetAddress( from, "JTerx Invoice"));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        emailSender.send(mimeMessage);
    }

    public void sendFactureByEmail(Facture facture, Client client) throws Exception{

    }



}


