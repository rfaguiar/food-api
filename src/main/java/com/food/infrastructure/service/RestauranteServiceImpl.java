package com.food.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.service.RestauranteService;
import com.food.service.model.CozinhaDto;
import com.food.service.model.RestauranteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    @Autowired
    public RestauranteServiceImpl(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    @Override
    public List<RestauranteDto> todos() {
        return restauranteRepository.todos()
                .map(RestauranteDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RestauranteDto> buscarPorId(Long restauranteId) {
        return restauranteRepository.porId(restauranteId)
                .map(RestauranteDto::new);
    }

    @Override
    public Optional<RestauranteDto> adicionar(RestauranteDto dto) {
        Cozinha cozinha = validarCozinha(dto.cozinha());
        return Optional.ofNullable(
                restauranteRepository.adicionar(
                        new Restaurante(dto.id(), dto.nome(), dto.taxaFrete(), cozinha)))
                .map(RestauranteDto::new);
    }

    private Cozinha validarCozinha(CozinhaDto dto) {
        return cozinhaRepository.porId(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        MessageFormat.format("Não existe cadastro de cozinha com código {0}",
                                dto.id())));
    }

    @Override
    public Optional<RestauranteDto> atualizar(Long restauranteId, RestauranteDto dto) {
        Cozinha cozinha = validarCozinha(dto.cozinha());
        return restauranteRepository.porId(restauranteId)
                .map(r -> restauranteRepository.adicionar(new Restaurante(r.id(), dto.nome(), dto.taxaFrete(), cozinha)))
                .map(RestauranteDto::new);
    }

    @Override
    public Optional<RestauranteDto> atualizarParcial(Long restauranteId, Map<String, Object> campos) {
        return restauranteRepository.porId(restauranteId)
                .map(r -> merge(campos, r))
                .map(restauranteRepository::adicionar)
                .map(RestauranteDto::new);
    }

    private Restaurante merge(Map<String, Object> dadosOrigem, final Restaurante restauranteDestino) {
        final RestauranteDto restauranteDtoDestino = new RestauranteDto(restauranteDestino);

        Field[] declaredRestauranteDtoFields = RestauranteDto.class.getDeclaredFields();
        Arrays.stream(declaredRestauranteDtoFields)
                .forEach(field -> putMapComDadosDoDestino(dadosOrigem, restauranteDtoDestino, field));

        ObjectMapper objectMapper = new ObjectMapper();
        RestauranteDto result = objectMapper.convertValue(dadosOrigem, RestauranteDto.class);

        return new Restaurante(result.id(), result.nome(), result.taxaFrete(), new Cozinha(result.cozinha().id(), result.cozinha().nome()));
    }

    private void putMapComDadosDoDestino(Map<String, Object> dadosOrigem, RestauranteDto restauranteDtoDestino, Field field) {
        try {
            String key = field.getName();
            Object value = ReflectionUtils.findMethod(RestauranteDto.class, field.getName()).invoke(restauranteDtoDestino);
            if (!dadosOrigem.containsKey(key)) {
                dadosOrigem.putIfAbsent(key, value);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
