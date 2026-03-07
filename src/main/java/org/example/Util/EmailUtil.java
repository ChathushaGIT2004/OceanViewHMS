package org.example.Util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    public   void sendPlainTextEmail(String toEmail, String subject, String body) {
        final String fromEmail = "chathushadewmin@gmail.com";
        final String appPassword = "cdpq kltj qzdz wsvv";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Ocean View Hotel Management")); // Sender name
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body); // plain text email

            Transport.send(message);
            System.out.println(" Email sent successfully to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Email failed: " + e.getMessage());
        }
    }


}