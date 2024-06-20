package com.food.api.v1.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.food.api.v1.assembler.FoodLinks;
import com.food.config.FoodSecurity;
import com.food.domain.model.Restaurante;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Optional;

@Relation(collectionRelation = "restaurantes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestauranteResponse extends RepresentationModel<RestauranteResponse> {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Thai Gourmet")
    private String nome;
    @Schema(example = "12.00")
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoResponse endereco;
    private CozinhaResponse cozinha;

    public RestauranteResponse() {}

    public RestauranteResponse(Restaurante restaurante) {
        this(restaurante.getId(), restaurante.getNome(), restaurante.getTaxaFrete(), restaurante.getAtivo(), restaurante.getAberto(),
                Optional.ofNullable(restaurante.getEndereco()).map(EnderecoResponse::new).orElse(null),
                new CozinhaResponse(restaurante.getCozinha()));
    }

    public RestauranteResponse(Long id, String nome, BigDecimal taxaFrete, Boolean ativo, Boolean aberto, EnderecoResponse endereco, CozinhaResponse cozinha) {
        this.id = id;
        this.nome = nome;
        this.taxaFrete = taxaFrete;
        this.ativo = ativo;
        this.aberto = aberto;
        this.endereco = endereco;
        this.cozinha = cozinha;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Boolean getAberto() {
        return aberto;
    }

    public EnderecoResponse getEndereco() {
        return endereco;
    }

    public CozinhaResponse getCozinha() {
        return cozinha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
    }

    public void setEndereco(EnderecoResponse endereco) {
        this.endereco = endereco;
    }

    public void setCozinha(CozinhaResponse cozinha) {
        this.cozinha = cozinha;
    }

    public RestauranteResponse addCozinhaLink(Link linkToCozinha) {
        cozinha.add(linkToCozinha);
        return this;
    }

    public RestauranteResponse addCidadeEnderecoLink(Link linkToCidade) {
        if (endereco != null && endereco.getCidade() != null) {
            endereco.getCidade().add(linkToCidade);
        }
        return this;
    }

    public RestauranteResponse addRestauranteStatusLink(FoodLinks foodLinks, FoodSecurity foodSecurity) {
        if (foodSecurity.podeGerenciarCadastroRestaurantes()) {
            if (this.ativacaoPermitida()) {
                this.add(foodLinks.linkToRestauranteAtivacao(id, "ativar"));
            }
            if (this.inativacaoPermitida()) {
                this.add(foodLinks.linkToRestauranteInativacao(id, "inativar"));
            }
        }
        if (foodSecurity.podeGerenciarFuncionamentoRestaurantes(this.id)) {
            if (this.aberturaPermitida()) {
            this.add(foodLinks.linkToRestauranteAbertura(id, "abrir"));
        }
            if (this.fechamentoPermitido()) {
                this.add(foodLinks.linkToRestauranteFechamento(id, "fechar"));
            }
        }
        return this;
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isInativo() {
        return !isAtivo();
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public boolean inativacaoPermitida() {
        return isAtivo();
    }

    public boolean fechamentoPermitido() {
        return isAberto();
    }

    public boolean aberturaPermitida() {
        return isAtivo() && isFechado();
    }

    public boolean ativacaoPermitida() {
        return isInativo();
    }
}
