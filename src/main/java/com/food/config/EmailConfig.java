package com.food.config;

import com.food.infrastructure.service.FakeEnvioEmailServiceImpl;
import com.food.infrastructure.service.SmtpEnvioEmailServiceImpl;
import com.food.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    @Value("${food.email.impl}")
    private String impl;

    @Bean
    public EnvioEmailService envioEmailService(JavaMailSender mailSender, freemarker.template.Configuration freemarkerConfig) {
        // Acho melhor usar switch aqui do que if/else if
        switch (impl) {
            case "FAKE":
                return new FakeEnvioEmailServiceImpl(mailSender, freemarkerConfig);
            case "SMTP":
                return new SmtpEnvioEmailServiceImpl(mailSender, freemarkerConfig);
            default:
                return null;
        }
    }
}
