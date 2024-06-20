package com.food;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Produto;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.ProdutoRepository;
import com.food.domain.repository.RestauranteRepository;
import com.food.util.BaseIntegrationTest;
import com.food.util.ResourceUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class CadastroRestauranteProdutoIT extends BaseIntegrationTest {

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;
    private static final int PRODUTO_INEXISTENTE_ID = 100;
    private int quantidadeProdutoRestauranteTay;
    private Produto produtoMacarrao;
    private Restaurante restauranteTay;
    private final String basePathRestaurantesProduto = "/v1/restaurantes/{restauranteId}/produtos";
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void begin() {
        super.configurarServer();
        prepararDados();
    }

    private void prepararDados() {
        Cozinha cozinha = cozinhaRepository.save(new Cozinha(null, "Cozinha teste", null));
        restauranteTay = restauranteRepository.save(new Restaurante(null, "Thai Delivery", BigDecimal.valueOf(9.50),
                null, null, Boolean.TRUE, Boolean.TRUE, null, cozinha, null, null, null));
        produtoMacarrao = produtoRepository.save(new Produto(null, "Macarrao", "Macarrao", BigDecimal.valueOf(10.0), Boolean.TRUE, restauranteTay));
        Produto produtoParmegiana = produtoRepository.save(new Produto(null, "Parmegiana", "Parmegiana", BigDecimal.valueOf(20.0), Boolean.TRUE, restauranteTay));
        quantidadeProdutoRestauranteTay = (int) produtoRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarProdutoDeUmrestauranteExistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.getId())
            .accept(ContentType.JSON)
            .when()
            .get(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.produtos", hasSize(quantidadeProdutoRestauranteTay))
            .body("_embedded.produtos.nome", hasItems("Parmegiana", "Macarrao"));
    }

    @Test
    void deveRetornar404QuandoConsultarProdutoDeUmrestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
            .when()
            .get(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar200QuandoConsultarProdutoDeUmRestauranteExistenteEProdutoExistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.getId())
                .pathParam("produtoId", produtoMacarrao.getId())
            .accept(ContentType.JSON)
        .when()
            .get(basePathRestaurantesProduto + "/{produtoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Macarrao"));
    }

    @Test
    void deveRetornar404QuandoConsultarProdutoDeUmRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .pathParam("produtoId", produtoMacarrao.getId())
            .accept(ContentType.JSON)
        .when()
            .get(basePathRestaurantesProduto + "/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar404QuandoConsultarProdutoDeUmRestauranteExistenteEProdutoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.getId())
            .pathParam("produtoId", PRODUTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get(basePathRestaurantesProduto + "/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar201QuandoAdicionarProdutoDeUmrestauranteExistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComNomeVazioDeUmRestauranteExistente() {
        String jsonProdutoNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/produto-nome-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoNomeVazio)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComDescricaoVazioDeUmRestauranteExistente() {
        String jsonProdutoDescricaoVazio = ResourceUtils.getContentFromResource(
                "/json/correto/produto-descricao-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoDescricaoVazio)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComPrecoNegativoDeUmRestauranteExistente() {
        String jsonProdutoPrecoNegativo = ResourceUtils.getContentFromResource(
                "/json/correto/produto-preco-negativo.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoPrecoNegativo)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComPrecoNuloDeUmRestauranteExistente() {
        String jsonProdutoPrecoNulo = ResourceUtils.getContentFromResource(
                "/json/correto/produto-preco-nulo.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoPrecoNulo)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAdicionarProdutoComAtivoNuloDeUmRestauranteExistente() {
        String jsonProdutoAtivoNulo = ResourceUtils.getContentFromResource(
                "/json/correto/produto-ativo-nulo.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoAtivoNulo)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantesProduto)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarProdutoDeUmrestauranteExistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .pathParam("produtoId", produtoMacarrao.getId())
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantesProduto + "/{produtoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornar404QuandoAtualizarProdutoDeUmrestauranteInexistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .pathParam("produtoId", produtoMacarrao.getId())
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantesProduto + "/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar404QuandoAtualizarProdutoInexistenteDeUmRestauranteExistente() {
        String jsonProdutoFeijoada = ResourceUtils.getContentFromResource(
                "/json/correto/produto-feijoada.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonProdutoFeijoada)
            .contentType(ContentType.JSON)
            .pathParam("restauranteId", restauranteTay.getId())
            .pathParam("produtoId", PRODUTO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantesProduto + "/{produtoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
