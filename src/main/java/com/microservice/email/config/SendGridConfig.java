package com.microservice.email.config;

import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(System.getenv("SENDGRID_API_KEY"));

    }
}
