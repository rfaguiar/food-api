package com.food.infrastructure.service;

import com.food.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Service
public class ProcessadorEmailTemplateService {

    @Autowired
    private Configuration freemarkerConfig;

    protected String processarTemplate(EnvioEmailService.Mensagem mensagem) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(mensagem.corpo());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.variaveis());
    }

}
