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
    private final String basePathEstados = "/v1/estados";
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;

    @BeforeEach
    public void begin() {
        super.configurarServer();
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
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", estadoSaoPaulo.id())
            .accept(ContentType.JSON)
        .when()
            .delete(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus409QuandoRemoverEstadoSendoUsadaPorUmaCidade() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", estadoGoias.id())
            .accept(ContentType.JSON)
        .when()
            .delete(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void deveRetornarStatus404QuandoRemoverEstadoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", ESTADO_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .delete(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarEstado() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", estadoSaoPaulo.id())
            .body(jsonCorretoEstadoAmazonas)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus400QuandoAtualizarEstado() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", estadoSaoPaulo.id())
            .body(jsonEstadoNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarEstadoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", ESTADO_ID_INEXISTENTE)
            .body(jsonCorretoEstadoAmazonas)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarEstado() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonCorretoEstadoAmazonas)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathEstados)
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarEstadoComNomeNulo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonEstadoNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathEstados)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarRespostaEStatusQuandoConsultarEstadoExistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", estadoSaoPaulo.id())
            .accept(ContentType.JSON)
        .when()
            .get(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(estadoSaoPaulo.nome()));
    }

    @Test
    void deveRetornarStatus404QuandoConsultarEstadoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("estadoId", ESTADO_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get(basePathEstados + "/{estadoId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarConterEstadosQuandoConsultarEstados() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathEstados)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.estadoResponseList", hasSize(quantidadeEstadosCadastrados))
            .body("_embedded.estadoResponseList.nome", hasItems("São Paulo", "Minas Gerais"));
    }

    @Test
    void deveRetornarStatus200QuandoConsultarEstados() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathEstados)
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}
