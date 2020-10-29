package com.food.service;

import com.food.api.v1.model.request.RestauranteRequest;
import com.food.api.v1.model.response.RestauranteResponse;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Restaurante;
import com.food.domain.model.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RestauranteService {
    List<Restaurante> todos();

    Restaurante buscarPorId(Long restauranteId);

    Restaurante adicionar(RestauranteRequest restaurante);

    Restaurante atualizar(Long restauranteId, RestauranteRequest restaurante);

    Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos);

    RestauranteResponse ativar(Long id);

    RestauranteResponse inativar(Long id);

    Set<FormaPagamento> listarFormasPagamentoPorId(Long id);

    void desassociarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId);

    void associarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId);

    void abrir(Long restauranteId);

    void fechar(Long restauranteId);

    List<Usuario> buscarUsuariosPorRestauranteId(Long restauranteId);

    void desassociarResponsavel(Long restauranteId, Long usuarioId);

    void associarResponsavel(Long restauranteId, Long usuarioId);

    void inativar(List<Long> restauranteIds);

    void ativar(List<Long> restauranteIds);
}
