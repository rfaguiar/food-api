package com.food.domain.listener;

import com.food.domain.event.PedidoCanceladoEvent;
import com.food.domain.model.Pedido;
import com.food.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    private final EnvioEmailService envioEmail;

    @Autowired
    public NotificacaoClientePedidoCanceladoListener(EnvioEmailService envioEmail) {
        this.envioEmail = envioEmail;
    }

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.pedido();
        var itens = pedido.getItens()
                .stream()
                .map(EnvioEmailService.ItemEmail::new)
                .collect(Collectors.toList());

        var mensagem = new EnvioEmailService.Mensagem(Set.of(pedido.getCliente().email()),
                pedido.getRestaurante().nome() + " - Pedido cancelado",
                "emails/pedido-cancelado.html",
                Map.of("nomeCliente", pedido.getCliente().nome(),
                        "nomeRestaurante", pedido.getRestaurante().nome(),
                        "itens", itens,
                        "taxaFrete", pedido.getTaxaFrete(),
                        "valorTotal", pedido.getValorTotal(),
                        "formaPagamentoDescricao", pedido.getFormaPagamento().descricao()));

        envioEmail.enviar(mensagem);
    }
}
