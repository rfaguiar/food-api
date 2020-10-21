package com.food.config;

import com.food.domain.repository.PedidoRepository;
import com.food.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class FoodSecurity {

    private final RestauranteRepository restauranteRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public FoodSecurity(RestauranteRepository restauranteRepository, PedidoRepository pedidoRepository) {
        this.restauranteRepository = restauranteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("usuario_id");
    }

    public boolean gerenciarRestaurante(Long restauranteId) {
        return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        if (restauranteId == null) {
            return false;
        }

        return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
    }

    public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }
}
