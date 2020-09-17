package com.food;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.ProdutoRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.util.BaseIntegrationTest;
import com.food.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

class CadastroRestauranteProdutoIT extends BaseIntegrationTest {

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;
    private static final int PRODUTO_INEXISTENTE_ID = 100;
    private int quantidadeProdutoRestauranteTay;
    private Produto produtoMacarrao;
    private Restaurante restauranteTay;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes/{restauranteId}/produtos";
        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {
        Cozinha cozinha = cozinhaRepository.save(new Cozinha(null, "Cozinha teste", null));
        restauranteTay = restauranteRepository.save(new Restaurante(null, "Thai Delivery", BigDecimal.valueOf(9.50),
                null, null, Boolean.TRUE, null, cozinha, null, null));
        produtoMacarrao = produtoRepository.save(new Produto(null, "Macarrao", "Macarrao", BigDecimal.valueOf(10.0), Boolean.TRUE, restauranteTay));
        Produto produtoParmegiana = produtoRepository.save(new Produto(null, "Parmegiana", "Parmegiana", BigDecimal.valueOf(20.0), Boolean.TRUE, restauranteTay));
        quantidadeProdutoRestauranteTay = (int) produtoRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarProdutoDeUmrestauranteExistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
            .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeProdutoRestauranteTay))
            .body("nome", hasItems("Parmegiana", "Macarrao"));
    }

    @Test
    void deveRetornar404QuandoConsultarProdutoDeUmrestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
            .when()
            .get()
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar200QuandoConsultarProdutoDeUmRestauranteExistenteEProdutoExistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
                .pathParam("produtoId", produtoMacarrao.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{produtoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Macarrao"));
    }

    @Test
    void deveRetornar404QuandoConsultarProdutoDeUmRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .pathParam("produtoId", produtoMacarrao.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar404QuandoConsultarProdutoDeUmRestauranteExistenteEProdutoInexistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .pathParam("produtoId", PRODUTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get("/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar201QuandoAdicionarProdutoDeUmrestauranteExistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");;
        given()
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComNomeVazioDeUmRestauranteExistente() {
        String jsonProdutoNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/produto-nome-vazio.json");;
        given()
            .body(jsonProdutoNomeVazio)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComDescricaoVazioDeUmRestauranteExistente() {
        String jsonProdutoDescricaoVazio = ResourceUtils.getContentFromResource(
                "/json/correto/produto-descricao-vazio.json");;
        given()
            .body(jsonProdutoDescricaoVazio)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComPrecoNegativoDeUmRestauranteExistente() {
        String jsonProdutoPrecoNegativo = ResourceUtils.getContentFromResource(
                "/json/correto/produto-preco-negativo.json");;
        given()
            .body(jsonProdutoPrecoNegativo)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComPrecoNuloDeUmRestauranteExistente() {
        String jsonProdutoPrecoNulo = ResourceUtils.getContentFromResource(
                "/json/correto/produto-preco-nulo.json");;
        given()
            .body(jsonProdutoPrecoNulo)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComAtivoNuloDeUmRestauranteExistente() {
        String jsonProdutoAtivoNulo = ResourceUtils.getContentFromResource(
                "/json/correto/produto-ativo-nulo.json");;
        given()
            .body(jsonProdutoAtivoNulo)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarProdutoDeUmrestauranteExistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");;
        given()
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .pathParam("produtoId", produtoMacarrao.id())
            .accept(ContentType.JSON)
        .when()
            .put("/{produtoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornar404QuandoAtualizarProdutoDeUmrestauranteInexistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");;
        given()
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .pathParam("produtoId", produtoMacarrao.id())
            .accept(ContentType.JSON)
        .when()
            .put("/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar404QuandoAtualizarProdutoInexistenteDeUmRestauranteExistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");;
        given()
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.id())
            .pathParam("produtoId", PRODUTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .put("/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
