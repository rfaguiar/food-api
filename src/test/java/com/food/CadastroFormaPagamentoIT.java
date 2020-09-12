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

class CadastroFormaPagamentoIT extends BaseIntegrationTest {

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
            .pathParam("formaPagamentoId", 100)
            .accept(ContentType.JSON)
        .when()
            .get("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
