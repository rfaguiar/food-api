package com.food.api.v1.assembler;

import com.food.api.v1.controller.PedidoController;
import com.food.api.v1.model.response.PedidoResponse;
import com.food.config.FoodSecurity;
import com.food.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResponseAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResponse> {

    private final FoodLinks foodLinks;
    private final FoodSecurity foodSecurity;

    @Autowired
    public PedidoResponseAssembler(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        super(PedidoController.class, PedidoResponse.class);
        this.foodLinks = foodLinks;
        this.foodSecurity = foodSecurity;
    }

    @Override
    public PedidoResponse toModel(Pedido pedido) {
        PedidoResponse pedidoResponse = new PedidoResponse(pedido)
                .add(foodLinks.linkToPedido(pedido.getCodigo()))
                .add(foodLinks.linkToPedidos("pedidos"))
                .addItensLink(foodLinks, foodSecurity)
                .addStatusLink(foodLinks, foodSecurity);

        if (foodSecurity.podeConsultarRestaurantes()) {
            pedidoResponse.addRestauranteLink(foodLinks.linkToRestaurante(pedido.getRestaurante().id()));
        }

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoResponse.addClientLink(foodLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        if (foodSecurity.podeConsultarFormasPagamento()) {
            pedidoResponse.addFormaPagamentoLink(foodLinks.linkToFormaPagamento(pedido.getFormaPagamento().id()));
        }

        if (foodSecurity.podeConsultarCidades()) {
            pedidoResponse.addCidadeEnderecoLink(foodLinks.linkToCidade(pedido.getEnderecoEntrega().cidade().id()));
        }

        return pedidoResponse;
    }

    @Override
    public CollectionModel<PedidoResponse> toCollectionModel(Iterable<? extends Pedido> pedidos) {
        return super.toCollectionModel(pedidos)
                .add(linkTo(PedidoController.class).withSelfRel());
    }
}
