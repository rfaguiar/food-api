package com.food;

import com.food.domain.model.Cozinha;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.CozinhaRepository;
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

class CadastroCozinhaIT extends BaseIntegrationTest {

    private static final int COZINHA_ID_INEXISTENTE = 100;

    private Cozinha cozinhaAmericana;
    private Cozinha cozinhaJaponesa;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;
    private String basePathCozinhas = "/v1/cozinhas";
    @Autowired
    protected CozinhaRepository cozinhaRepository;
    @Autowired
    protected RestauranteRepository restauranteRepository;


    @BeforeEach
    public void begin() {
        super.configurarServer();
        prepararDados();
        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");
    }

    private void prepararDados() {
        cozinhaJaponesa = cozinhaRepository.save(new Cozinha(null, "Japonesa", null));
        restauranteRepository.save(new Restaurante(null, "Tay chi", BigDecimal.valueOf(10L), null,
                null, Boolean.TRUE, Boolean.TRUE, null, cozinhaJaponesa, null, null, null));
        cozinhaRepository.save(new Cozinha(null, "Tailandesa", null));
        cozinhaAmericana = new Cozinha(null, "Americana", null);
        cozinhaRepository.save(cozinhaAmericana);
        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }

    @Test
    void deveRetornarStatus200QuandoConsultarCozinhas() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathCozinhas)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarConterCozinhasQuandoConsultarCozinhas() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathCozinhas)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.cozinhas", hasSize(quantidadeCozinhasCadastradas))
            .body("_embedded.cozinhas.nome", hasItems("Americana", "Tailandesa"));
    }

    @Test
    void deveRetornarRespostaEStatusQuandoConsultarCozinhaExistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cozinhaId", cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when()
            .get(basePathCozinhas + "/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    void deveRetornarStatus404QuandoConsultarCozinhaInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get(basePathCozinhas + "/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarCozinha() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathCozinhas)
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarCozinhaComNomeNulo() {
        String jsonCozinhaNomeNulo = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-nome-null.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonCozinhaNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathCozinhas)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus204QuandoRemoverCozinha() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cozinhaId", cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when()
            .delete(basePathCozinhas + "/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus404QuandoRemoverCozinhaInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .delete(basePathCozinhas + "/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus409QuandoRemoverCozinhaSendoUsadaPorUmRestaurante() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cozinhaId", cozinhaJaponesa.getId())
            .accept(ContentType.JSON)
        .when()
            .delete(basePathCozinhas + "/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarCozinhaInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathCozinhas + "/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarCozinha() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cozinhaId", cozinhaAmericana.getId())
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathCozinhas + "/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}
