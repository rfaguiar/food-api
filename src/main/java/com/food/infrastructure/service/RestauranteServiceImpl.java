package com.food.infrastructure.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.api.v1.model.request.RestauranteRequest;
import com.food.api.v1.model.response.RestauranteResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public List<Restaurante> todos() {
        return restauranteRepository.findAll();
    }

    @Override
    public Restaurante buscarPorId(Long restauranteId) {
        return buscarPorIdEValidar(restauranteId);
    }

    @Override
    public Restaurante adicionar(RestauranteRequest dto) {
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
        return restauranteRepository.save(restaurante);
    }

    @Override
    public Restaurante atualizar(Long restauranteId, RestauranteRequest dto) {
        Restaurante antigo = buscarPorIdEValidar(restauranteId);
        Cozinha cozinha = validarCozinha(dto.cozinha().id());
        Cidade cidade = validarCidade(dto.endereco().cidade().id());
        Endereco endereco = new Endereco(dto.endereco().cep(),
                dto.endereco().logradouro(),
                dto.endereco().numero(),
                dto.endereco().complemento(),
                dto.endereco().bairro(),
                cidade);
        return restauranteRepository.save(new Restaurante(antigo.id(), dto.nome(), dto.taxaFrete(),
                antigo.dataCadastro(), antigo.dataAtualizacao(), antigo.ativo(), Boolean.TRUE, endereco,
                cozinha, antigo.formasPagamento(), antigo.produtos(), antigo.responsaveis()));
    }

    @Override
    public Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos) {
        Restaurante antigo = buscarPorIdEValidar(restauranteId);
        Restaurante restaurante = merge(campos, antigo);
        return restauranteRepository.save(restaurante);
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
    public Set<FormaPagamento> listarFormasPagamentoPorId(Long id) {
        Restaurante restaurante = buscarPorIdEValidar(id);
        return restaurante.formasPagamento();
    }

    @Override
    @Transactional
    public void desassociarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Override
    @Transactional
    public void associarFormaPagamentoPorId(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
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
    public List<Usuario> buscarUsuariosPorRestauranteId(Long restauranteId) {
        Restaurante restaurante = buscarPorIdEValidar(restauranteId);
        return new ArrayList<>(restaurante.responsaveis());
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

    @Override
    @Transactional
    public void inativar(List<Long> restauranteIds) {
        try {
            restauranteIds.forEach(this::inativar);
        } catch (RestauranteNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void ativar(List<Long> restauranteIds) {
        try {
            restauranteIds.forEach(this::ativar);
        } catch (RestauranteNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private void validate(RestauranteResponse restaurante) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, "RestauranteDto");
        validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private Restaurante merge(Map<String, Object> dadosOrigem, final Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        final RestauranteResponse restauranteDtoDestino = new RestauranteResponse(restauranteDestino);
        RestauranteResponse restauranteOrigem = objectMapper.convertValue(dadosOrigem, RestauranteResponse.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(RestauranteResponse.class, nomePropriedade);
            assert field != null;
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restauranteDtoDestino, novoValor);
        });

        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        validate(restauranteDtoDestino);

        return new Restaurante(restauranteDtoDestino.getId(), restauranteDtoDestino.getNome(), restauranteDtoDestino.getTaxaFrete(),
                restauranteDestino.dataCadastro(),
                restauranteDestino.dataAtualizacao(),
                restauranteDestino.ativo(),
                Boolean.TRUE,
                restauranteDestino.endereco(),
                new Cozinha(restauranteDtoDestino.getCozinha().getId(), restauranteDtoDestino.getCozinha().getNome(), null),
                restauranteDestino.formasPagamento(),
                restauranteDestino.produtos(),
                restauranteDestino.responsaveis());
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

    Restaurante buscarPorIdEValidar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradaException(id));
    }

}
