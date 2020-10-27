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

class CadastroCidadeIT extends BaseIntegrationTest {

    private final int CIDADE_ID_INEXISTENTE = 200;
    private int quantidadeCidadesCadastrados;
    private String jsonCorretoCidadeGoiania;
    private String jsonCorretoCidadeComEstadoInexistente;
    private String jsonCidadeNomeNulo;
    private Cidade cidadeSaoPaulo;
    private Cidade beloHorizonte;
    private String basePath = "/v1/cidades";
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        databaseCleaner.clearTables();
        prepararDados();
        prepararOAuthUsers();
        jsonCorretoCidadeGoiania = ResourceUtils.getContentFromResource(
                "/json/correto/cidade-goiania.json");
        jsonCorretoCidadeComEstadoInexistente = ResourceUtils.getContentFromResource(
                "/json/correto/cidade-estado-inexistente.json");
        jsonCidadeNomeNulo = ResourceUtils.getContentFromResource(
                "/json/correto/cidade-nome-null.json");
    }

    private void prepararDados() {
        Estado estadoSaoPaulo = estadoRepository.save(new Estado(null, "São Paulo"));
        Estado estadoMinasGerais = estadoRepository.save(new Estado(null, "Minas Gerais"));
        cidadeSaoPaulo = cidadeRepository.save(new Cidade(null, "São Paulo", estadoSaoPaulo));
        beloHorizonte = cidadeRepository.save(new Cidade(null, "Belo Horizonte", estadoMinasGerais));
        Cozinha cozinhaJaponesa = cozinhaRepository.save(new Cozinha(null, "Japonesa", null));
        restauranteRepository.save(new Restaurante(null, "Tay chi", BigDecimal.valueOf(10L),
                null, null, Boolean.TRUE, Boolean.TRUE,
                new Endereco(null, null, null, null, null, beloHorizonte),
                cozinhaJaponesa,
                null, null, null));
        quantidadeCidadesCadastrados = (int) cidadeRepository.count();
    }

    @Test
    void deveRetornarStatus204QuandoRemoverCidade() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", cidadeSaoPaulo.id())
            .accept(ContentType.JSON)
        .when()
            .delete(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornarStatus409QuandoRemoverEstadoSendoUsadaPorUmaCidade() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", beloHorizonte.id())
            .accept(ContentType.JSON)
        .when()
            .delete(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void deveRetornarStatus404QuandoRemoverCidadeInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", CIDADE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .delete(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus200QuandoAtualizarEstado() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", cidadeSaoPaulo.id())
            .body(jsonCorretoCidadeGoiania)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarStatus400QuandoAtualizarCidade() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", cidadeSaoPaulo.id())
            .body(jsonCidadeNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarCidadeInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", CIDADE_ID_INEXISTENTE)
            .body(jsonCorretoCidadeGoiania)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarCidade() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonCorretoCidadeGoiania)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePath)
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarCidadeComEstadoInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonCorretoCidadeComEstadoInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePath)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400QuandoCadastrarCidadeComNomeNulo() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonCidadeNomeNulo)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePath)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarRespostaEStatusQuandoConsultaCidadeInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", cidadeSaoPaulo.id())
            .accept(ContentType.JSON)
        .when()
            .get(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cidadeSaoPaulo.nome()));
    }

    @Test
    void deveRetornarStatus404QuandoConsultarCidadeInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("cidadeId", CIDADE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get(basePath + "/{cidadeId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarConterCidadesQuandoConsultarCidades() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePath)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.cidades", hasSize(quantidadeCidadesCadastrados))
            .body("_embedded.cidades.nome", hasItems("São Paulo", "Belo Horizonte"));
    }

    @Test
    void deveRetornarStatus200QuandoConsultarCidades() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePath)
        .then()
            .statusCode(HttpStatus.OK.value());
    }
}
