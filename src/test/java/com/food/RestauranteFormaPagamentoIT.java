package com.food;

import com.food.domain.model.Cozinha;
import com.food.domain.model.FormaPagamento;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.FormaPagamentoRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.util.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

class RestauranteFormaPagamentoIT extends BaseIntegrationTest {

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;
    private static final int FORMA_PAGAMENTO_INEXISTENTE_ID = 100;
    private int quantidadeFormasPagamentoRestauranteTay;
    private FormaPagamento formaPagamentoDinheiro;
    private Restaurante restauranteTay;
    private Restaurante restauranteTukTuk;
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes/{restauranteId}/formas-pagamento";
        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {
        formaPagamentoDinheiro = formaPagamentoRepository.save(new FormaPagamento(null, "Dinheiro"));
        FormaPagamento formaPagamentoCartaoCredito = formaPagamentoRepository.save(new FormaPagamento(null, "Cartão de crédito"));
        FormaPagamento formaPagamentoCartaoDebito = formaPagamentoRepository.save(new FormaPagamento(null, "Cartão de débito"));
        Set<FormaPagamento> formaPagamentos = Set.of(formaPagamentoCartaoCredito, formaPagamentoCartaoDebito, formaPagamentoDinheiro);
        Cozinha cozinha = cozinhaRepository.save(new Cozinha(null, "Cozinha teste", null));
        restauranteTay = restauranteRepository.save(new Restaurante(null, "Thai Delivery", BigDecimal.valueOf(9.50),
                null, null, Boolean.TRUE, null, cozinha, formaPagamentos, null));
        restauranteTukTuk = restauranteRepository.save(new Restaurante(null, "Tuk Tuk Comida Indiana", BigDecimal.valueOf(9.50),
                null, null, Boolean.TRUE, null, cozinha, null, null));
        quantidadeFormasPagamentoRestauranteTay = (int) formaPagamentoRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarFormasDePagamentoDeUmrestauranteExistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
            .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeFormasPagamentoRestauranteTay))
            .body("descricao", hasItems("Cartão de crédito", "Cartão de débito", "Dinheiro"));
    }

    @Test
    void deveRetornar404QuandoConsultarFormasDePagamentoDeUmrestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
            .when()
            .get()
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoAssociarFormasDePagamentoDeUmrestauranteExistente() {
        given()
            .pathParam("restauranteId", restauranteTukTuk.id())
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .put("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornar404QuandoAssociarFormasDePagamentoDeUmRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .put("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar404QuandoAssociarFormasDePagamentoDeUmaFormaDePagamentoInexistente() {
        given()
            .pathParam("restauranteId", restauranteTukTuk.id())
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .put("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoDesassociarFormasDePagamentoDeUmrestauranteExistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornar404QuandoDesassociarFormasDePagamentoDeUmRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .pathParam("formaPagamentoId", formaPagamentoDinheiro.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar404QuandoDesassociarFormasDePagamentoDeUmaFormaDePagamentoInexistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .pathParam("formaPagamentoId", FORMA_PAGAMENTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .delete("/{formaPagamentoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
