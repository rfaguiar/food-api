package com.food.infrastructure.service;

import freemarker.template.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class FakeEnvioEmailServiceImpl extends SmtpEnvioEmailServiceImpl {

    private static final Logger LOGGER = LogManager.getLogger(FakeEnvioEmailServiceImpl.class);

    @Autowired
    public FakeEnvioEmailServiceImpl(JavaMailSender mailSender, Configuration freemarkerConfig) {
        super(mailSender, freemarkerConfig);
    }

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processarTemplate(mensagem);
            LOGGER.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.destinatarios(), corpo);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
