package com.food.infrastructure.service;

import com.food.service.EnvioEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class FakeEnvioEmailServiceImpl implements EnvioEmailService {

    private static final Logger LOGGER = LogManager.getLogger(FakeEnvioEmailServiceImpl.class);
    @Autowired
    private ProcessadorEmailTemplateService processadorEmailTemplateService;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processadorEmailTemplateService.processarTemplate(mensagem);
            LOGGER.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.destinatarios(), corpo);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
