package com.food.infrastructure.service;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class SandboxEnvioEmailServiceImpl extends SmtpEnvioEmailServiceImpl {

    @Value("${food.email.sandbox.destinatario}")
    private String destinatario;

    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = super.criarMimeMessage(mensagem);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(destinatario);
        return mimeMessage;
    }
}
