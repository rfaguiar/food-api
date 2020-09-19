package com.food.infrastructure.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.api.model.request.RestauranteRequest;
import com.food.api.model.response.FormaPagamentoResponse;
import com.food.api.model.response.RestauranteResponse;
import com.food.api.model.response.UsuarioResponse;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.RestauranteNaoEncontradaException;
import com.food.domain.exception.ValidacaoException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Endereco;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Restaurante;
import com.food.domain.model.Usuario;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.service.FormaPagamentoService;
import com.food.service.RestauranteService;
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
    private final CidadeRepository cidadeRepository;
    private final FormaPagamentoService formaPagamentoService;
    private final UsuarioServiceImpl usuarioService;

    @Autowired
    public RestauranteServiceImpl(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository, SmartValidator validator, CidadeRepository cidadeRepository, FormaPagamentoService formaPagamentoService, UsuarioServiceImpl usuarioService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
        this.validator = validator;
        this.cidadeRepository = cidadeRepository;
        this.formaPagamentoService = formaPagamentoService;
        this.usuarioService = usuarioService;
    }

    @Override
    public List<RestauranteResponse> todos() {
        return restauranteRepository.findAll().stream()
                .map(RestauranteResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public RestauranteResponse buscarPorId(Long restauranteId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        return new RestauranteResponse(restaurante);
    }

    @Override
    public RestauranteResponse adicionar(RestauranteRequest dto) {
        Cozinha cozinha = validarCozinha(dto.cozinha().id());
        Cidade cidade = validarCidade(dto.endereco().cidade().id());
        Endereco endereco = new Endereco(dto.endereco().cep(),
                dto.endereco().logradouro(),
                dto.endereco().numero(),
                dto.endereco().complemento(),
                dto.endereco().bairro(),
                cidade);
        Restaurante restaurante = new Restaurante(null, dto.nome(), dto.taxaFrete(), null,
                null,
                Boolean.TRUE,
                Boolean.TRUE,
                endereco,
                cozinha,
                null,
                null,
                null);
        return new RestauranteResponse(restauranteRepository.save(
                restaurante));
    }

    @Override
    public RestauranteResponse atualizar(Long restauranteId, RestauranteRequest dto) {
        Restaurante antigo = buscarPorIdEValidar(restauranteId);
        Cozinha cozinha = validarCozinha(dto.cozinha().id());
        Cidade cidade = validarCidade(dto.endereco().cidade().id());
        Endereco endereco = new Endereco(dto.endereco().cep(),
                dto.endereco().logradouro(),
                dto.endereco().numero(),
                dto.endereco().complemento(),
                dto.endereco().bairro(),
                cidade);
        Restaurante novo = restauranteRepository.save(new Restaurante(antigo.id(), dto.nome(), dto.taxaFrete(),
                antigo.dataCadastro(), antigo.dataAtualizacao(), antigo.ativo(), Boolean.TRUE, endereco,
                cozinha, antigo.formasPagamento(), antigo.produtos(), antigo.responsaveis()));
        return new RestauranteResponse(novo);
    }

    @Override
    public RestauranteResponse atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request) {
        Restaurante antigo = buscarPorIdEValidar(restauranteId);
        Restaurante restaurante = merge(campos, antigo, request);
        Restaurante novo = restauranteRepository.save(restaurante);
        return new RestauranteResponse(novo);
    }

    @Override
    @Transactional
    public RestauranteResponse ativar(Long id) {
        Restaurante restaurante = buscarPorIdEValidar(id);
        restaurante = new Restaurante(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                restaurante.dataCadastro(),restaurante.dataAtualizacao(), Boolean.TRUE, Boolean.TRUE, restaurante.endereco(),
                restaurante.cozinha(), restaurante.formasPagamento(), restaurante.produtos(), restaurante.responsaveis());
        restauranteRepository.save(restaurante);
        return new RestauranteResponse(restaurante);
    }

    @Transactional
    public RestauranteResponse inativar(Long id) {
        Restaurante restaurante = buscarPorIdEValidar(id);
        restaurante = new Restaurante(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                restaurante.dataCadastro(),restaurante.dataAtualizacao(), Boolean.FALSE, Boolean.TRUE, restaurante.endereco(),
                restaurante.cozinha(), restaurante.formasPagamento(), restaurante.produtos(), restaurante.responsaveis());
        restauranteRepository.save(restaurante);
        return new RestauranteResponse(restaurante);
    }

    @Override
    public List<FormaPagamentoResponse> listarFormasPagamentoPorId(Long id) {
        Restaurante restaurante = buscarPorIdEValidar(id);
        return restaurante.formasPagamento()
                .stream().map(FormaPagamentoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void desassociarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        FormaPagamentoResponse response = formaPagamentoService.buscarPorId(formaPagamentoId);
        FormaPagamento formaPagamento = new FormaPagamento(response.id(), response.descricao());
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Override
    @Transactional
    public void associarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        FormaPagamentoResponse response = formaPagamentoService.buscarPorId(formaPagamentoId);
        restaurante.adicionarFormaPagamento(new FormaPagamento(response.id(), response.descricao()));
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        restaurante = new Restaurante(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                restaurante.dataCadastro(),restaurante.dataAtualizacao(), restaurante.ativo(), Boolean.TRUE, restaurante.endereco(),
                restaurante.cozinha(), restaurante.formasPagamento(), restaurante.produtos(), restaurante.responsaveis());
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        restaurante = new Restaurante(restaurante.id(), restaurante.nome(), restaurante.taxaFrete(),
                restaurante.dataCadastro(),restaurante.dataAtualizacao(), restaurante.ativo(), Boolean.FALSE, restaurante.endereco(),
                restaurante.cozinha(), restaurante.formasPagamento(), restaurante.produtos(), restaurante.responsaveis());
        restauranteRepository.save(restaurante);
    }

    @Override
    public List<UsuarioResponse> buscarUsuariosPorRestauranteId(Long restauranteId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        return restaurante.responsaveis()
                .stream()
                .map(UsuarioResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        Usuario usuario = usuarioService.buscarEValidarPorId(usuarioId);
        restaurante.removerResponsavel(usuario);
    }

    @Override
    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        Usuario usuario = usuarioService.buscarEValidarPorId(usuarioId);
        restaurante.adicionarResponsavel(usuario);
    }

    private void validate(RestauranteResponse restaurante) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, "RestauranteDto");
        validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private Restaurante merge(Map<String, Object> dadosOrigem, final Restaurante restauranteDestino, HttpServletRequest request) {
        final RestauranteResponse restauranteDtoDestino = new RestauranteResponse(restauranteDestino);

        Field[] declaredRestauranteDtoFields = RestauranteResponse.class.getDeclaredFields();
        Arrays.stream(declaredRestauranteDtoFields)
                .forEach(field -> putMapComDadosDoDestino(dadosOrigem, restauranteDtoDestino, field));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            RestauranteResponse result = objectMapper.convertValue(dadosOrigem, RestauranteResponse.class);
            validate(result);

            return new Restaurante(result.id(), result.nome(), result.taxaFrete(),
                    restauranteDestino.dataCadastro(),
                    restauranteDestino.dataAtualizacao(),
                    restauranteDestino.ativo(),
                    Boolean.TRUE,
                    restauranteDestino.endereco(),
                    new Cozinha(result.cozinha().id(), result.cozinha().nome(), null),
                    restauranteDestino.formasPagamento(),
                    restauranteDestino.produtos(),
                    restauranteDestino.responsaveis());
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, new ServletServerHttpRequest(request));
        }
    }

    private void putMapComDadosDoDestino(Map<String, Object> dadosOrigem, RestauranteResponse restauranteDtoDestino, Field field) {
        try {
            String key = field.getName();
            Object value = Objects.requireNonNull(ReflectionUtils.findMethod(RestauranteResponse.class, field.getName()))
                    .invoke(restauranteDtoDestino);
            if (!dadosOrigem.containsKey(key)) {
                dadosOrigem.putIfAbsent(key, value);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Cidade validarCidade(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new NegocioException(
                        MessageFormat.format("Não existe cadastro de cidade com código {0}", id)));
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
