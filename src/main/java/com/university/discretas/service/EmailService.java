package com.university.discretas.service;

import com.university.discretas.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private JavaMailSender sender;

    public boolean sendEmail(EmailDTO emailBody)  {
        return sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject());
    }


    private boolean sendEmailTool(String textMessage, String email,String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            send = true;
        } catch (MessagingException e) {
            e.getStackTrace();
        }
        return send;
    }

}
