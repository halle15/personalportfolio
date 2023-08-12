package mmd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mmd.models.Message;

@Service
public class MailNotificationService {

    @Autowired
    private JavaMailSender emailSender;
    
    @Async
    public void sendSimpleMessage(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("evannhall02@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    
    public void sendMessageNotification(Message message) {
    	
    }
}
