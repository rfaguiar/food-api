package com.food.domain.listener;

import com.food.domain.event.PedidoConfirmadoEvent;
import com.food.domain.model.Pedido;
import com.food.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    private final EnvioEmailService envioEmail;

    @Autowired
    public NotificacaoClientePedidoConfirmadoListener(EnvioEmailService envioEmail) {
        this.envioEmail = envioEmail;
    }

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.pedido();
        var itens = pedido.getItens()
                .stream()
                .map(EnvioEmailService.ItemEmail::new)
                .collect(Collectors.toList());
        var mensagem = new EnvioEmailService.Mensagem(Set.of(pedido.getCliente().getEmail()),
                pedido.getRestaurante().getNome() + "- Pedido confirmado",
                "emails/pedido-confirmado.html",
                Map.of("nomeCliente", pedido.getCliente().getNome(),
                        "nomeRestaurante", pedido.getRestaurante().getNome(),
                        "itens", itens,
                        "taxaFrete", pedido.getTaxaFrete(),
                        "valorTotal", pedido.getValorTotal(),
                        "formaPagamentoDescricao", pedido.getFormaPagamento().getDescricao()));
        envioEmail.enviar(mensagem);
    }
}
