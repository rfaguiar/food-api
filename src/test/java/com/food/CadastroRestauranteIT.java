package com.food;

import com.food.domain.model.Cidade;
import com.food.domain.model.Cozinha;
import com.food.domain.model.Endereco;
import com.food.domain.model.Estado;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.repository.CozinhaRepository;
import com.food.domain.repository.EstadoRepository;
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
    private String jsonRestauranteComCidadeInexistente;
    private Restaurante restauranteTay;
    private final String basePathRestaurantes = "/v1/restaurantes";
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    @BeforeEach
    public void begin() {
        super.configurarServer();
        prepararDados();
    }

    private void prepararDados() {
        Estado estado = estadoRepository.save(new Estado(null, "estado_teste"));
        Cidade cidade = cidadeRepository.save(new Cidade(null, "cidade_teste", estado));
        Cozinha cozinha = cozinhaRepository.save(new Cozinha(null, "Cozinha teste", null));
        Endereco endereco = new Endereco("cep_teste", "logradouro_teste", "numero_teste",
                "complemento_teste", "bairro_teste", cidade);
        restauranteTay = restauranteRepository.save(new Restaurante(null, "Thai Delivery", BigDecimal.valueOf(9.50),
                null, null, Boolean.TRUE, Boolean.TRUE, endereco, cozinha, null, null, null));
        restauranteRepository.save(new Restaurante(null, "Tuk Tuk Comida Indiana", BigDecimal.valueOf(9.50),
                null, null, Boolean.TRUE, Boolean.TRUE, endereco, cozinha, null, null, null));
        quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
        jsonCorretoRestauranteLanchonete = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-lanchonete.json");

        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-cozinha-inexistente.json");

        jsonRestauranteComCidadeInexistente = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-cidade-inexistente.json");
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarParcialRestauranteComNovaTaxaFrete() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.id())
            .body("{\"taxaFrete\": 180}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .patch(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus400QuandoAtualizarParcialRestauranteComCozinhaInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.id())
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .patch(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarParcialRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .body("{\"taxaFrete\": 180}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .patch(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus400QuandoAtualizarRestauranteComCozinhaInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.id())
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoAtualizarRestauranteComCidadeInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
        .pathParam("restauranteId", restauranteTay.id())
            .body(jsonRestauranteComCidadeInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .body(jsonCorretoRestauranteLanchonete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarRestaurante() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.id())
            .body(jsonCorretoRestauranteLanchonete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComTaxaFreteNegativa() {
        String jsonRestauranteComTaxafreteNegativa = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-taxa-frete-negativa.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonRestauranteComTaxafreteNegativa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComNomeVazio() {
        String jsonRestauranteComNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-nome-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonRestauranteComNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComPropriedadeInvalida() {
        String jsonRestauranteComPropriedadeInvalida = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-propriedade-invalida.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonRestauranteComPropriedadeInvalida)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComJsonInvalido() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body("{,}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComCozinhaInvalida() {
        String jsonRestauranteComCozinhaInvalida = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-cozinha-invalida.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonRestauranteComCozinhaInvalida)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComCozinhaNula() {
        String jsonRestauranteComCozinhaNula = ResourceUtils.getContentFromResource(
                "/json/correto/restaurante-cozinha-nula.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonRestauranteComCozinhaNula)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComCozinhaInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    void deveRetornarStatus400QuandoCadastrarRestauranteComCidadeInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
                .body(jsonRestauranteComCidadeInexistente)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .post(basePathRestaurantes)
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarRestaurante() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonCorretoRestauranteLanchonete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus404QuandoConsultarRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus400QuandoConsultarRestauranteComParametroInvalido() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
                .pathParam("restauranteId", "a")
                .accept(ContentType.JSON)
                .when()
                .get(basePathRestaurantes + "/{restauranteId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus404QuandoConsultarRestauranteRecursoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get(basePathRestaurantes + "/{restauranteId}/a")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarRespostaEStatusQuandoConsultarRestauranteExistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .get(basePathRestaurantes + "/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(restauranteTay.nome()));
    }

    @Test
    void deveRetornarConterRestaurantesQuandoConsultarRestaurantes() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.restaurantes", hasSize(quantidadeRestaurantesCadastrados))
            .body("_embedded.restaurantes.nome", hasItems("Thai Delivery", "Tuk Tuk Comida Indiana"));
    }

    @Test
    void deveRetornarStatus200QuandoConsultarRestaurantes() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathRestaurantes)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus204QuandoAtualizarEstadoParaAtivo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", 1)
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}/ativo")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus204QuandoAtualizarEstadoParaInativo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", 1)
            .accept(ContentType.JSON)
        .when()
            .delete(basePathRestaurantes + "/{restauranteId}/ativo")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarEstadoParaAtivoComRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}/ativo")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarEstadoParaInativoComRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .delete(basePathRestaurantes + "/{restauranteId}/ativo")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus204QuandoAbrirRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}/abertura")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus204QuandoFecharRestauranteInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("restauranteId", restauranteTay.id())
            .accept(ContentType.JSON)
        .when()
            .put(basePathRestaurantes + "/{restauranteId}/fechamento")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
