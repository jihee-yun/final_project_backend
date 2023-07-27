package com.kh.finalProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "dandelion1141@gmail.com";
    private static int number;
    private static Map<String, String> verificationCodes = new HashMap<>();

    public static void createNumber(String mail){
        number = (int)(Math.random() * (90000)) + 100000;
        verificationCodes.put(mail, String.valueOf(number));
    }

    public MimeMessage CreateMail(String mail){
        createNumber(mail);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public boolean confirm(String mail) {
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        return true;
    }

    public boolean verifyCode(String email, String code) {
        String savedCode = verificationCodes.get(email);
        return code.equals(savedCode);
    }

}
