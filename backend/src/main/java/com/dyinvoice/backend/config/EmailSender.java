package com.dyinvoice.backend.config;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
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


    public void sendEmail(String from, String to, String subject, String name) throws Exception {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        Context context = new Context();
        context.setVariable("name", name);

        String content = templateEngine.process("mail-template", context); // "email-template" is the name of your HTML template

        helper.setFrom(new InternetAddress( from, "JTerx Invoice"));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true); // set true for isHtml
        emailSender.send(mimeMessage);
    }

}


