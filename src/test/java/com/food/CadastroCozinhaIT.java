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

class CadastroCozinhaIT extends BaseIntegrationTest {

    private static final int COZINHA_ID_INEXISTENTE = 100;

    private Cozinha cozinhaAmericana;
    private Cozinha cozinhaJaponesa;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;
    @Autowired
    protected CozinhaRepository cozinhaRepository;
    @Autowired
    protected RestauranteRepository restauranteRepository;


    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
        databaseCleaner.clearTables();
        prepararDados();
        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");
    }

    private void prepararDados() {
        cozinhaJaponesa = cozinhaRepository.save(new Cozinha(null, "Japonesa", null));
        restauranteRepository.save(new Restaurante(null, "Tay chi", BigDecimal.valueOf(10L), null,
                null, null, cozinhaJaponesa, null, null));
        cozinhaRepository.save(new Cozinha(null, "Tailandesa", null));
        cozinhaAmericana = new Cozinha(null, "Americana", null);
        cozinhaRepository.save(cozinhaAmericana);
        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }

    @Test
    void deveRetornarStatus200QuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarConterCozinhasQuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeCozinhasCadastradas))
            .body("nome", hasItems("Americana", "Tailandesa"));
    }

    @Test
    void deveRetornarRespostaEStatusQuandoConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", cozinhaAmericana.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaAmericana.nome()));
    }

    @Test
    void deveRetornarStatus404QuandoConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarCozinha() {
        given()
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarCozinhaComNomeNulo() {
        String jsonCozinhaNomeNulo = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-nome-null.json");
        given()
            .body(jsonCozinhaNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus204QuandoRemoverCozinha() {
        given()
            .pathParam("cozinhaId", cozinhaAmericana.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus404QuandoRemoverCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .delete("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus409QuandoRemoverCozinhaSendoUsadaPorUmRestaurante() {
        given()
            .pathParam("cozinhaId", cozinhaJaponesa.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarCozinha() {
        given()
            .pathParam("cozinhaId", cozinhaAmericana.id())
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}
