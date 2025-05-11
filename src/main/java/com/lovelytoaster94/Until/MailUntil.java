package com.lovelytoaster94.Until;

import com.lovelytoaster94.Pojo.User;
import com.lovelytoaster94.Service.UserService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class MailUntil {
    private static UserService userService;

    public static void setUserService(UserService service) {
        userService = service;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

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

    public static User checkEmailRepeat(String email, String userName) {
        User searchUser = new User();
        searchUser.setEmail(email);
        List<User> userList = userService.searchUserInfo(searchUser);
        if (userList != null && !userList.isEmpty()) {
            if (!userList.getFirst().getUserName().equals(userName))
                return userList.getFirst();
        }
        return null;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
