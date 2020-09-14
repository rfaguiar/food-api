package com.food;

import com.food.domain.model.Grupo;
import com.food.domain.repository.GrupoRepository;
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

class CadastroGrupoIT extends BaseIntegrationTest {

    private static final int GRUPO_INEXISTENTE_ID = 100;
    private int quantidadeGruposCadastrados;
    private Grupo grupoSecretaria;
    @Autowired
    private GrupoRepository grupoRepository;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/grupos";
        databaseCleaner.clearTables();
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
        given()
            .accept(ContentType.JSON)
            .when()
        .get()
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterGrupoQuandoConsultar() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeGruposCadastrados))
            .body("nome", hasItems("Secretária", "Gerente", "Vendedor"));
    }

    @Test
    void deveRetornar200QuandoConsultarUmGrupo() {
        given()
            .pathParam("grupoId", grupoSecretaria.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{grupoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterGrupoQuandoConsultarPassandooId() {
        given()
            .pathParam("grupoId", grupoSecretaria.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{grupoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(grupoSecretaria.id().intValue()))
            .body("nome", equalTo(grupoSecretaria.nome()));
    }

    @Test
    void deveRetornar404QuandoConsultarUmaGrupoInexistente() {
        given()
            .pathParam("grupoId", GRUPO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get("/{grupoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoCadastrarUmNovoGrupo() {
        given()
            .body("{\"nome\":\"Ajudante\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("nome", equalTo("Ajudante"));
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoGrupoComNomeVazio() {
        given()
            .body("{\"nome\":\"   \"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoGrupoComNomeNulo() {
        given()
            .body("{\"nome\": null}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovoGrupo() {
        given()
                .pathParam("grupoId", grupoSecretaria.id())
            .body("{\"nome\":\"Estagiario\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{grupoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(grupoSecretaria.id().intValue()))
            .body("nome", equalTo("Estagiario"));
    }

    @Test
    void deveRetornar404QuandoAtualizarUmNovaGrupoInexistente() {
        given()
            .pathParam("grupoId", GRUPO_INEXISTENTE_ID)
            .body("{\"nome\":\"Estagiario\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{grupoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaGrupoVazio() {
        given()
            .pathParam("grupoId", grupoSecretaria.id())
            .body("{\"nome\":\"   \"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{grupoId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaGrupoNulo() {
        given()
            .pathParam("grupoId", grupoSecretaria.id())
            .body("{\"nome\": null}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{grupoId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar204QuandoRemoverUmGrupo() {
        given()
            .pathParam("grupoId", grupoSecretaria.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{grupoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
    @Test
    void deveRetornar404QuandoRemoverUmaGrupoInexistente() {
        given()
            .pathParam("grupoId", GRUPO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .delete("/{grupoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
