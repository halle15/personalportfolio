package mmd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String subject, String text) {
    	System.out.println("SENT MAIL!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("evannhall02@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
