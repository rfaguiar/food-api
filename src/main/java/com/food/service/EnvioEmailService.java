package com.food.service;

import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    record Mensagem (Set<String> destinatarios,
                     String assunto,
                     String corpo,
                     Map<String, Object> variaveis){}
}
