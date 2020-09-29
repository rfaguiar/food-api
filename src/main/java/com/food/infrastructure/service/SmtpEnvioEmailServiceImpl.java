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
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

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
            MimeMessage mimeMessage = criarMimeMessage(mensagem);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail", e);
        }
    }

    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException, IOException, TemplateException {
        String corpo = processarTemplate(mensagem);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(remetente);
        helper.setTo(mensagem.destinatarios().toArray(new String[0]));
        helper.setSubject(mensagem.assunto());
        helper.setText(corpo, true);
        return mimeMessage;
    }

    protected String processarTemplate(Mensagem mensagem) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(mensagem.corpo());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.variaveis());
    }
}
