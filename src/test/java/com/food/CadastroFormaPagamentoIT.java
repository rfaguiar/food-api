package com.food;

import com.food.domain.model.FormaPagamento;
import com.food.domain.repository.FormaPagamentoRepository;
import com.food.util.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
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
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    private FormaPagamento formaPagamentoDinheiro;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/formas-pagamento";
        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {
        formaPagamentoRepository.saveAll(List.of(
                new FormaPagamento(null, "Cartão de crédito"),
                new FormaPagamento(null, "Cartão de débito")));
        formaPagamentoDinheiro = formaPagamentoRepository.save(new FormaPagamento(null, "Dinheiro"));
        quantidadeFormasPagamentoCadastrados = (int) formaPagamentoRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarFormasDePagamento() {
        given()
            .accept(ContentType.JSON)
            .when()
        .get()
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterFormasPagamentoQuandoConsultar() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeFormasPagamentoCadastrados))
            .body("descricao", hasItems("Cartão de crédito", "Cartão de débito", "Dinheiro"));
    }

    @Test
    void deveRetornar200QuandoConsultarUmaFormaDePagamento() {
        given()
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterFormaPagamentoQuandoConsultarPassandooId() {
        given()
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(formaPagamentoDinheiro.id().intValue()))
            .body("descricao", equalTo(formaPagamentoDinheiro.descricao()));
    }

    @Test
    void deveRetornar404QuandoConsultarUmaFormaDePagamentoInexistente() {
        given()
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoCadastrarUmNovaFormaDePagamento() {
        given()
            .body("{\"descricao\":\"Cartão Refeição Alelo\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("descricao", equalTo("Cartão Refeição Alelo"));
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovaFormaDePagamento() {
        given()
                .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .body("{\"descricao\":\"Cartão Refeição Alelo\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(formaPagamentoDinheiro.id().intValue()))
            .body("descricao", equalTo("Cartão Refeição Alelo"));
    }

    @Test
    void deveRetornar404QuandoAtualizarUmNovaFormaDePagamentoInexistente() {
        given()
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .body("{\"descricao\":\"Cartão Refeição Alelo\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoRemoverUmaFormaDePagamento() {
        given()
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornar404QuandoRemoverUmaFormaDePagamentoInexistente() {
        given()
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .delete("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
