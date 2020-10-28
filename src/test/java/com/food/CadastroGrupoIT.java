package com.food;

import com.food.domain.model.Grupo;
import com.food.domain.repository.GrupoRepository;
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

class CadastroGrupoIT extends BaseIntegrationTest {

    private static final int GRUPO_INEXISTENTE_ID = 100;
    private int quantidadeGruposCadastrados;
    private Grupo grupoSecretaria;
    private final String basePathGrupo = "/v1/grupos";
    @Autowired
    private GrupoRepository grupoRepository;

    @BeforeEach
    public void begin() {
        super.configurarServer();
        prepararDados();
    }

    private void prepararDados() {
        grupoRepository.saveAll(List.of(
                new Grupo(null, "Vendedor", null),
                new Grupo(null, "Gerente", null)));
        grupoSecretaria = grupoRepository.save(new Grupo(null, "Secretária", null));
        quantidadeGruposCadastrados = (int) grupoRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarGrupos() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathGrupo)
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterGrupoQuandoConsultar() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathGrupo)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.grupos", hasSize(quantidadeGruposCadastrados))
            .body("_embedded.grupos.nome", hasItems("Secretária", "Gerente", "Vendedor"));
    }

    @Test
    void deveRetornar200QuandoConsultarUmGrupo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", grupoSecretaria.id())
            .accept(ContentType.JSON)
        .when()
            .get(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterGrupoQuandoConsultarPassandooId() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", grupoSecretaria.id())
            .accept(ContentType.JSON)
        .when()
            .get(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(grupoSecretaria.id().intValue()))
            .body("nome", equalTo(grupoSecretaria.nome()));
    }

    @Test
    void deveRetornar404QuandoConsultarUmaGrupoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", GRUPO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoCadastrarUmNovoGrupo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body("{\"nome\":\"Ajudante\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathGrupo)
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("nome", equalTo("Ajudante"));
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoGrupoComNomeVazio() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body("{\"nome\":\"   \"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathGrupo)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoGrupoComNomeNulo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body("{\"nome\": null}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathGrupo)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovoGrupo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", grupoSecretaria.id())
            .body("{\"nome\":\"Estagiario\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(grupoSecretaria.id().intValue()))
            .body("nome", equalTo("Estagiario"));
    }

    @Test
    void deveRetornar404QuandoAtualizarUmNovaGrupoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", GRUPO_INEXISTENTE_ID)
            .body("{\"nome\":\"Estagiario\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaGrupoVazio() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", grupoSecretaria.id())
            .body("{\"nome\":\"   \"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaGrupoNulo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", grupoSecretaria.id())
            .body("{\"nome\": null}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar204QuandoRemoverUmGrupo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", grupoSecretaria.id())
            .accept(ContentType.JSON)
        .when()
            .delete(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
    @Test
    void deveRetornar404QuandoRemoverUmaGrupoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("grupoId", GRUPO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .delete(basePathGrupo + "/{grupoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
