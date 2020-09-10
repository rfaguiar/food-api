package com.food;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
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

class CadastroRestauranteIT extends BaseIntegrationTest {

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;
    private int quantidadeRestaurantesCadastrados;
    private String jsonCorretoRestauranteLanchonete;
    private String jsonRestauranteComCozinhaInexistente;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;
    private Restaurante restauranteTay;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";
        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {
        Cozinha cozinha = cozinhaRepository.save(new Cozinha(null, "Cozinha teste", null));
        restauranteTay = restauranteRepository.save(new Restaurante(null, "Thai Delivery", BigDecimal.valueOf(9.50),
                null, null, null,cozinha, null, null));
        restauranteRepository.save(new Restaurante(null, "Tuk Tuk Comida Indiana", BigDecimal.valueOf(9.50),
                null, null, null, cozinha, null, null));
        quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
        jsonCorretoRestauranteLanchonete = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-lanchonete.json");

        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-cozinha-inexistente.json");
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarParcialRestauranteComNovaTaxaFrete() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .body("{\"taxaFrete\": 180}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .patch("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

     /*

### Patch a restaurante, bad request
PATCH http://localhost:8080/restaurantes/3
Content-Type: application/json

{
  "nome": "Comida Mineira",
  "cozinha": {
    "id": 10
  }
}*/

//    @Test
    void deveRetornarStatus400QuandoAtualizarParcialRestauranteComCozinhaInexistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .patch("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarParcialRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .body("{\"taxaFrete\": 180}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .patch("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus400QuandoAtualizarRestaurante() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .body(jsonCorretoRestauranteLanchonete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarRestaurante() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .body(jsonCorretoRestauranteLanchonete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComTaxaFreteNegativa() {
        String jsonRestauranteComTaxafreteNegativa = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-taxa-frete-negativa.json");
        given()
            .body(jsonRestauranteComTaxafreteNegativa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComNomeVazio() {
        String jsonRestauranteComNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-nome-vazio.json");
        given()
            .body(jsonRestauranteComNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComCozinhaNula() {
        String jsonRestauranteComCozinhaNula = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-cozinha-nula.json");
        given()
            .body(jsonRestauranteComCozinhaNula)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarRestaurante() {
        given()
            .body(jsonCorretoRestauranteLanchonete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus404QuandoConsultarRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarRespostaEStatusQuandoConsultarRestauranteExistente() {
        given()
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(restauranteTay.nome()));
    }

    @Test
    void deveRetornarConterRestaurantesQuandoConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeRestaurantesCadastrados))
            .body("nome", hasItems("Thai Delivery", "Tuk Tuk Comida Indiana"));
    }

    @Test
    void deveRetornarStatus200QuandoConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}