package com.lovelytoaster94.Until;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailUntil {
    public static void sendMail(String toEmail, String code) throws MessagingException {
        final String fromEmail = "verdantgem@qq.com";
        final String authCode = "rkzloxqjcllebbab";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, authCode);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("验证码验证");
        message.setText("您的验证码是：" + code + "，5分钟内有效。");

        Transport.send(message);
    }
}
