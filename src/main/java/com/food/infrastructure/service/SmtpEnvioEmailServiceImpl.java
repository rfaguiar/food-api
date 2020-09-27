package com.food.infrastructure.service;

import com.food.domain.exception.EmailException;
import com.food.service.EnvioEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SmtpEnvioEmailServiceImpl implements EnvioEmailService {

    private static final Logger LOGGER = LogManager.getLogger(SmtpEnvioEmailServiceImpl.class);
    private final JavaMailSender mailSender;
    @Value("${food.mail.remetente}")
    private String remetente;

    @Autowired
    public SmtpEnvioEmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(mensagem.destinatarios().toArray(new String[0]));
            mimeMessageHelper.setSubject(mensagem.assunto());
            mimeMessageHelper.setText(mensagem.corpo(), true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new EmailException("Não foi possível enviar e-mail", e);
        }
    }
}
