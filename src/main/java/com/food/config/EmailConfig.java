package com.food.config;

import com.food.infrastructure.service.FakeEnvioEmailServiceImpl;
import com.food.infrastructure.service.SandboxEnvioEmailServiceImpl;
import com.food.infrastructure.service.SmtpEnvioEmailServiceImpl;
import com.food.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${food.email.impl}")
    private String impl;

    @Bean
    public EnvioEmailService envioEmailService() {
        return switch (impl) {
            case "FAKE" -> new FakeEnvioEmailServiceImpl();
            case "SMTP" -> new SmtpEnvioEmailServiceImpl();
            case "SANDBOX" -> new SandboxEnvioEmailServiceImpl();
            default -> null;
        };
    }
}
