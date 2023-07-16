package com.kh.finalProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 비밀번호 이메일 인증
    private String ePw = createKey();

    // 이메일 작성
    private MimeMessage createEmailMsg(String to) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("[SweetKingdom] 임시 비밀번호");

        String msg = "";
        msg += "<p>안녕하세요!</p>";
        msg += "<p>SweetKingdom에서 임시비밀번호를 확인해주세요</p>";
        msg += "<h3>임시 비밀번호  : "  +ePw + "</h3>";
        msg += "<p>감사합니다.</p>";

        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress("sweetkingdom@naver.com", "SweetKingdom"));

        return message;
    }

    // 인증키 생성
    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3);
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    // 이메일 보내기
    public String sendSimpleMessage(String to) throws Exception {

        MimeMessage message = createEmailMsg(to); // 메일 발송
        try {// 예외처리
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }
}
