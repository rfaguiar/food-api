package com.food.infrastructure.service;

import com.food.domain.exception.EmailException;
import com.food.service.EnvioEmailService;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class SmtpEnvioEmailServiceImpl implements EnvioEmailService {

    private static final Logger LOGGER = LogManager.getLogger(SmtpEnvioEmailServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ProcessadorEmailTemplateService processadorEmailTemplateService;
    @Value("${food.mail.remetente}")
    private String remetente;


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
        String corpo = processadorEmailTemplateService.processarTemplate(mensagem);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(remetente);
        helper.setTo(mensagem.destinatarios().toArray(new String[0]));
        helper.setSubject(mensagem.assunto());
        helper.setText(corpo, true);
        return mimeMessage;
    }

}
