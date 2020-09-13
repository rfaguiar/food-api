package com.food.infrastructure.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.RestauranteNaoEncontradaException;
import com.food.domain.exception.ValidacaoException;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.service.RestauranteService;
import com.food.service.model.RestauranteDto;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;
    private final SmartValidator validator;

    @Autowired
    public RestauranteServiceImpl(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository, SmartValidator validator) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.validator = validator;
    }

    @Override
    public List<RestauranteDto> todos() {
        return restauranteRepository.findAll().stream()
                .map(RestauranteDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public RestauranteDto buscarPorId(Long restauranteId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        return new RestauranteDto(restaurante);
    }

    @Override
    public RestauranteDto adicionar(RestauranteDto dto) {
        Cozinha cozinha = validarCozinha(dto.cozinha().id());
        return new RestauranteDto(restauranteRepository.save(
                    new Restaurante(null, dto.nome(), dto.taxaFrete(), null,
                            null,
                            Boolean.TRUE,
                            null,
                            cozinha,
                            null,
                            null)));
    }

    @Override
    public RestauranteDto atualizar(Long restauranteId, RestauranteDto dto) {
        Restaurante antigo = buscarPorIdEValidar(restauranteId);
        Cozinha cozinha = validarCozinha(dto.cozinha().id());
        Restaurante novo = restauranteRepository.save(new Restaurante(antigo.id(), dto.nome(), dto.taxaFrete(),
                antigo.dataCadastro(), antigo.dataAtualizacao(), antigo.ativo(), antigo.endereco(),
                cozinha, antigo.formasPagamento(), antigo.produtos()));
        return new RestauranteDto(novo);
    }

    @Override
    public RestauranteDto atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request) {
        Restaurante antigo = buscarPorIdEValidar(restauranteId);
        Restaurante restaurante = merge(campos, antigo, request);
        Restaurante novo = restauranteRepository.save(restaurante);
        return new RestauranteDto(novo);
    }

    @Override
    @Transactional
    public RestauranteDto ativar(Long id) {
        Restaurante restaurante = buscarPorIdEValidar(id);
        restaurante = new Restaurante(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                restaurante.dataCadastro(),restaurante.dataAtualizacao(), Boolean.TRUE, restaurante.endereco(),
                restaurante.cozinha(), restaurante.formasPagamento(), restaurante.produtos());
        restauranteRepository.save(restaurante);
        return new RestauranteDto(restaurante);
    }

    @Transactional
    public RestauranteDto inativar(Long id) {
        Restaurante restaurante = buscarPorIdEValidar(id);
        restaurante = new Restaurante(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                restaurante.dataCadastro(),restaurante.dataAtualizacao(), Boolean.FALSE, restaurante.endereco(),
                restaurante.cozinha(), restaurante.formasPagamento(), restaurante.produtos());
        restauranteRepository.save(restaurante);
        return new RestauranteDto(restaurante);
    }

    private void validate(RestauranteDto restaurante) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, "RestauranteDto");
        validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private Restaurante merge(Map<String, Object> dadosOrigem, final Restaurante restauranteDestino, HttpServletRequest request) {
        final RestauranteDto restauranteDtoDestino = new RestauranteDto(restauranteDestino);

        Field[] declaredRestauranteDtoFields = RestauranteDto.class.getDeclaredFields();
        Arrays.stream(declaredRestauranteDtoFields)
                .forEach(field -> putMapComDadosDoDestino(dadosOrigem, restauranteDtoDestino, field));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            RestauranteDto result = objectMapper.convertValue(dadosOrigem, RestauranteDto.class);
            validate(result);

            return new Restaurante(result.id(), result.nome(), result.taxaFrete(),
                    restauranteDestino.dataCadastro(),
                    restauranteDestino.dataAtualizacao(),
                    restauranteDestino.ativo(),
                    restauranteDestino.endereco(),
                    new Cozinha(result.cozinha().id(), result.cozinha().nome(), null),
                    restauranteDestino.formasPagamento(),
                    restauranteDestino.produtos());
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, new ServletServerHttpRequest(request));
        }
    }

    private void putMapComDadosDoDestino(Map<String, Object> dadosOrigem, RestauranteDto restauranteDtoDestino, Field field) {
        try {
            String key = field.getName();
            Object value = Objects.requireNonNull(ReflectionUtils.findMethod(RestauranteDto.class, field.getName()))
                    .invoke(restauranteDtoDestino);
            if (!dadosOrigem.containsKey(key)) {
                dadosOrigem.putIfAbsent(key, value);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Cozinha validarCozinha(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new NegocioException(
                        MessageFormat.format("Não existe cadastro de cozinha com código {0}", id)));
    }

    private Restaurante buscarPorIdEValidar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradaException(id));
    }

}
