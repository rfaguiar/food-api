package com.food;

import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;
import com.food.domain.repository.EstadoRepository;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

class CadastroEstadoIT extends BaseIntegrationTest {

    private static final int ESTADO_ID_INEXISTENTE = 200;
    private Estado estadoSaoPaulo;
    private Estado estadoGoias;
    private int quantidadeEstadosCadastrados;
    private String jsonCorretoEstadoAmazonas;
    private String jsonEstadoNomeNulo;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/estados";
        databaseCleaner.clearTables();
        prepararDados();
        jsonCorretoEstadoAmazonas = ResourceUtils.getContentFromResource(
                "/json/correto/estado-amazonas.json");

        jsonEstadoNomeNulo = ResourceUtils.getContentFromResource(
                "/json/correto/estado-nome-null.json");
    }

    private void prepararDados() {
        estadoSaoPaulo = estadoRepository.save(new Estado(null, "São Paulo"));
        estadoRepository.save(new Estado(null, "Minas Gerais"));
        estadoGoias = estadoRepository.save(new Estado(null, "Goias"));
        cidadeRepository.save(new Cidade(null, "Goiania", estadoGoias));
        quantidadeEstadosCadastrados = (int) estadoRepository.count();
    }

    @Test
    void deveRetornarStatus204QuandoRemoverEstado() {
        given()
            .pathParam("estadoId", estadoSaoPaulo.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{estadoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus409QuandoRemoverEstadoSendoUsadaPorUmaCidade() {
        given()
            .pathParam("estadoId", estadoGoias.id())
            .accept(ContentType.JSON)
        .when()
            .delete("/{estadoId}")
        .then()
            .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void deveRetornarStatus404QuandoRemoverEstadoInexistente() {
        given()
            .pathParam("estadoId", ESTADO_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .delete("/{estadoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarEstado() {
        given()
            .pathParam("estadoId", estadoSaoPaulo.id())
            .body(jsonCorretoEstadoAmazonas)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{estadoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus400QuandoAtualizarEstado() {
        given()
            .pathParam("estadoId", estadoSaoPaulo.id())
            .body(jsonEstadoNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{estadoId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarCozinhaInexistente() {
        given()
            .pathParam("estadoId", ESTADO_ID_INEXISTENTE)
            .body(jsonCorretoEstadoAmazonas)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{estadoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarEstado() {
        given()
            .body(jsonCorretoEstadoAmazonas)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarEstadoComNomeNulo() {
        given()
            .body(jsonEstadoNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarRespostaEStatusQuandoConsultarEstadoExistente() {
        given()
            .pathParam("estadoId", estadoSaoPaulo.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{estadoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(estadoSaoPaulo.nome()));
    }

    @Test
    void deveRetornarStatus404QuandoConsultarEstadoInexistente() {
        given()
            .pathParam("estadoId", ESTADO_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{estadoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarConterEstadosQuandoConsultarEstados() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeEstadosCadastrados))
            .body("nome", hasItems("São Paulo", "Minas Gerais"));
    }

    @Test
    void deveRetornarStatus200QuandoConsultarEstados() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}
