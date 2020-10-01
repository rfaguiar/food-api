package com.food.domain.listener;

import com.food.domain.event.PedidoConfirmadoEvent;
import com.food.domain.model.Pedido;
import com.food.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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

    @EventListener
    public void aoConfirmas(PedidoConfirmadoEvent event) {
        Pedido pedido = event.pedido();
        var itens = pedido.getItens()
                .stream()
                .map(EnvioEmailService.ItemEmail::new)
                .collect(Collectors.toList());
        var mensagem = new EnvioEmailService.Mensagem(Set.of(pedido.getCliente().email()),
                pedido.getRestaurante().nome() + "- Pedido confirmado",
                "pedido-confirmado.html",
                Map.of("nomeCliente", pedido.getCliente().nome(),
                        "nomeRestaurante", pedido.getRestaurante().nome(),
                        "itens", itens,
                        "taxaFrete", pedido.getTaxaFrete(),
                        "valorTotal", pedido.getValorTotal(),
                        "formaPagamentoDescricao", pedido.getFormaPagamento().descricao()));
        envioEmail.enviar(mensagem);
    }
}
