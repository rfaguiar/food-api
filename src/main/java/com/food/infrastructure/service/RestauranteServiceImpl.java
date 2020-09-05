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
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.food.infrastructure.repository.spec.RestauranteSpecFactory.comFreteGratis;
import static com.food.infrastructure.repository.spec.RestauranteSpecFactory.comNomeSemelhante;

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
        return restauranteRepository.findAll().stream()
                .map(RestauranteDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RestauranteDto> buscarPorId(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .map(RestauranteDto::new);
    }

    @Override
    public List<RestauranteDto> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal)
                .map(RestauranteDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RestauranteDto> adicionar(RestauranteDto dto) {
        Cozinha cozinha = validarCozinha(dto.cozinha());
        return Optional.of(restauranteRepository.save(
                    new Restaurante(dto.id(), dto.nome(), dto.taxaFrete(), null,
                            null, null, cozinha, null, null)))
                .map(RestauranteDto::new);
    }

    private Cozinha validarCozinha(CozinhaDto dto) {
        return cozinhaRepository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        MessageFormat.format("Não existe cadastro de cozinha com código {0}",
                                dto.id())));
    }

    @Override
    public Optional<RestauranteDto> atualizar(Long restauranteId, RestauranteDto dto) {
        Cozinha cozinha = validarCozinha(dto.cozinha());
        return restauranteRepository.findById(restauranteId)
                .map(r -> restauranteRepository.save(new Restaurante(r.id(), dto.nome(), dto.taxaFrete(),
                        r.dataCadastro(), r.dataAtualizacao(), r.endereco(), cozinha, r.formasPagamento(),
                        r.produtos())))
                .map(RestauranteDto::new);
    }

    @Override
    public Optional<RestauranteDto> atualizarParcial(Long restauranteId, Map<String, Object> campos) {
        return restauranteRepository.findById(restauranteId)
                .map(r -> merge(campos, r))
                .map(restauranteRepository::save)
                .map(RestauranteDto::new);
    }

    @Override
    public List<RestauranteDto> restaurantesComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)))
                .stream()
                .map(RestauranteDto::new)
                .collect(Collectors.toList());
    }

    private Restaurante merge(Map<String, Object> dadosOrigem, final Restaurante restauranteDestino) {
        final RestauranteDto restauranteDtoDestino = new RestauranteDto(restauranteDestino);

        Field[] declaredRestauranteDtoFields = RestauranteDto.class.getDeclaredFields();
        Arrays.stream(declaredRestauranteDtoFields)
                .forEach(field -> putMapComDadosDoDestino(dadosOrigem, restauranteDtoDestino, field));

        ObjectMapper objectMapper = new ObjectMapper();
        RestauranteDto result = objectMapper.convertValue(dadosOrigem, RestauranteDto.class);

        return new Restaurante(result.id(), result.nome(), result.taxaFrete(),
                restauranteDestino.dataCadastro(),
                restauranteDestino.dataAtualizacao(),
                restauranteDestino.endereco(),
                new Cozinha(result.cozinha().id(), result.cozinha().nome(), null),
                restauranteDestino.formasPagamento(),
                restauranteDestino.produtos());
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

}
