package com.food;

import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;
import com.food.util.BaseIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

class CadastroFormaPagamentoIT extends BaseIntegrationTest {

    private static final int FORMA_PAGAMENTO_INEXISTENTE_ID = 100;
    private int quantidadeFormasPagamentoCadastrados;
    private final String basePathFormaPagamento = "/v1/formas-pagamento";
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    private FormaPagamento formaPagamentoDinheiro;

    @BeforeEach
    public void begin() {
        super.configurarServer();
        prepararDados();
    }

    private void prepararDados() {
        formaPagamentoRepository.saveAll(List.of(
                new FormaPagamento("Cartão de crédito"),
                new FormaPagamento("Cartão de débito")));
        formaPagamentoDinheiro = formaPagamentoRepository.save(new FormaPagamento("Dinheiro"));
        quantidadeFormasPagamentoCadastrados = (int) formaPagamentoRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarFormasDePagamento() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathFormaPagamento)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterFormasPagamentoQuandoConsultar() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathFormaPagamento)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.formasPagamento", hasSize(quantidadeFormasPagamentoCadastrados))
            .body("_embedded.formasPagamento.descricao", hasItems("Cartão de crédito", "Cartão de débito", "Dinheiro"));
    }

    @Test
    void deveRetornar200QuandoConsultarUmaFormaDePagamento() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .get(basePathFormaPagamento + "/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterFormaPagamentoQuandoConsultarPassandooId() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .get(basePathFormaPagamento + "/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(formaPagamentoDinheiro.id().intValue()))
            .body("descricao", equalTo(formaPagamentoDinheiro.descricao()));
    }

    @Test
    void deveRetornar404QuandoConsultarUmaFormaDePagamentoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get(basePathFormaPagamento + "/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoCadastrarUmNovaFormaDePagamento() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body("{\"descricao\":\"Cartão Refeição Alelo\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathFormaPagamento)
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("descricao", equalTo("Cartão Refeição Alelo"));
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovaFormaDePagamento() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .body("{\"descricao\":\"Cartão Refeição Alelo\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathFormaPagamento + "/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(formaPagamentoDinheiro.id().intValue()))
            .body("descricao", equalTo("Cartão Refeição Alelo"));
    }

    @Test
    void deveRetornar404QuandoAtualizarUmNovaFormaDePagamentoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .body("{\"descricao\":\"Cartão Refeição Alelo\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathFormaPagamento + "/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoRemoverUmaFormaDePagamento() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .delete(basePathFormaPagamento + "/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornar404QuandoRemoverUmaFormaDePagamentoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .delete(basePathFormaPagamento + "/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
