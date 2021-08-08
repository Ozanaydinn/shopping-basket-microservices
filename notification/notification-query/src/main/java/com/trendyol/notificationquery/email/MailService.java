package com.trendyol.notificationquery.email;

import com.trendyol.notificationcore.models.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {
    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender  = mailSender;
    }

    public void sendMail(NotificationType type, String destination) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destination);
        switch (type) {
            case PRICE_REDUCED:
                message.setSubject("SALES!");
                message.setText("An item in your basket is in sale!");
                break;
            case STOCK_LESS_THAN_THREE:
                message.setSubject("Item almost running out!");
                message.setText("An item in your basket is nearly out of stock!");
                break;
            case OUT_OF_STOCK:
                message.setSubject("Out of stock");
                message.setText("An item in your basket is currently out of stock");
        }
    }
}
