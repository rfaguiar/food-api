package com.food.infrastructure.service;

import com.food.domain.exception.EmailException;
import com.food.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Service
public class SmtpEnvioEmailServiceImpl implements EnvioEmailService {

    private static final Logger LOGGER = LogManager.getLogger(SmtpEnvioEmailServiceImpl.class);
    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;
    @Value("${food.mail.remetente}")
    private String remetente;

    @Autowired
    public SmtpEnvioEmailServiceImpl(JavaMailSender mailSender, Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            var corpo = processarTemplate(mensagem);
            var mimeMessage = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(mensagem.destinatarios().toArray(new String[0]));
            mimeMessageHelper.setSubject(mensagem.assunto());
            mimeMessageHelper.setText(corpo, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new EmailException("Não foi possível enviar e-mail", e);
        }
    }

    private String processarTemplate(Mensagem mensagem) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(mensagem.corpo());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.variaveis());
    }


}
