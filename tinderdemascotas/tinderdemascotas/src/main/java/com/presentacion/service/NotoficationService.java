
package com.presentacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotoficationService {
    
    /*
    @Autowired
    private JavaMailSender mailSender;
    
    @Async
    public void sendEmail(String body, String title, String email){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("noreplay@tinder-mascota.com");
        mail.setSubject(title);
        mail.setText(body);
        
        mailSender.send(mail);
    }
    */
}
