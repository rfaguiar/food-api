package com.food.service;

import com.food.api.model.request.RestauranteRequest;
import com.food.api.model.response.FormaPagamentoResponse;
import com.food.api.model.response.RestauranteResponse;
import com.food.api.model.response.UsuarioResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RestauranteService {
    List<RestauranteResponse> todos();

    RestauranteResponse buscarPorId(Long restauranteId);

    RestauranteResponse adicionar(RestauranteRequest restaurante);

    RestauranteResponse atualizar(Long restauranteId, RestauranteRequest restaurante);

    RestauranteResponse atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request);

    RestauranteResponse ativar(Long id);

    RestauranteResponse inativar(Long id);

    List<FormaPagamentoResponse> listarFormasPagamentoPorId(Long id);

    void desassociarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId);

    void associarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId);

    void abrir(Long restauranteId);

    void fechar(Long restauranteId);

    List<UsuarioResponse> buscarUsuariosPorRestauranteId(Long restauranteId);

    void desassociarResponsavel(Long restauranteId, Long usuarioId);

    void associarResponsavel(Long restauranteId, Long usuarioId);

    void inativar(List<Long> restauranteIds);

    void ativar(List<Long> restauranteIds);
}
