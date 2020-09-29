package com.food.service;

import com.food.domain.model.ItemPedido;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    record Mensagem (Set<String> destinatarios,
                     String assunto,
                     String corpo,
                     Map<String, Object> variaveis){}


    class ItemEmail {
        private Integer quantidade;
        private BigDecimal precoTotal;
        private String nomeProduto;
        public ItemEmail(ItemPedido itemPedido) {
            quantidade = itemPedido.quantidade();
            precoTotal = itemPedido.precoTotal();
            nomeProduto = itemPedido.produto().nome();
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public BigDecimal getPrecoTotal() {
            return precoTotal;
        }

        public String getNomeProduto() {
            return nomeProduto;
        }
    }
}
