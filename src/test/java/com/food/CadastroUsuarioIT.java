package com.food;

import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;
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

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

class CadastroUsuarioIT extends BaseIntegrationTest {

    private static final int USUARIO_INEXISTENTE_ID = 100;
    private int quantidadeUsuariosCadastrados;
    private Usuario usuarioJoao;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void begin() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        RestAssured.basePath = "/usuarios";
        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {
        usuarioRepository.saveAll(List.of(
                new Usuario(null, "Maria Joaquina", "maria.vnd@food.com", "123", null, null),
                new Usuario(null, "José Souza", "jose.aux@food.com", "123", null, null)));
        usuarioJoao = usuarioRepository.save(new Usuario(null, "João da Silva", "joao.ger@food.com", "123", null, null));
        quantidadeUsuariosCadastrados = (int) usuarioRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarUsuarios() {
        given()
            .accept(ContentType.JSON)
            .when()
        .get()
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterUsuarioQuandoConsultar() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize(quantidadeUsuariosCadastrados))
            .body("nome", hasItems("Maria Joaquina", "José Souza", "João da Silva"));
    }

    @Test
    void deveRetornar200QuandoConsultarUmUsuario() {
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterUsuarioQuandoConsultarPassandooId() {
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .accept(ContentType.JSON)
        .when()
            .get("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(usuarioJoao.id().intValue()))
            .body("nome", equalTo(usuarioJoao.nome()))
            .body("email", equalTo(usuarioJoao.email()));
    }

    @Test
    void deveRetornar404QuandoConsultarUmaUsuarioInexistente() {
        given()
            .pathParam("usuarioId", USUARIO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoCadastrarUmNovoUsuario() {
        String jsonUsuario = ResourceUtils.getContentFromResource(
                "/json/correto/usuario.json");
        given()
            .body(jsonUsuario)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("nome", equalTo("Daniel"))
            .body("email", equalTo("dani@food.com"));
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComEmailExistente() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-email-existente.json");;
        given()
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComNomeVazio() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-nome-vazio.json");;
        given()
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComSenhaVazio() {
        String jsonUsuarioSenhaVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-senha-vazio.json");;
        given()
            .body(jsonUsuarioSenhaVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComEmailVazio() {
        String jsonUsuarioEmailVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-email-vazio.json");;
        given()
            .body(jsonUsuarioEmailVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComEmailInvalido() {
        String jsonUsuarioEmailInvalidoVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-email-invalido.json");;
        given()
            .body(jsonUsuarioEmailInvalidoVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovoUsuario() {
        String jsonUsuario = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar.json");
        given()
                .pathParam("usuarioId", usuarioJoao.id())
            .body(jsonUsuario)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(usuarioJoao.id().intValue()))
            .body("nome", equalTo("Daniel"))
            .body("email", equalTo("dani@food.com"));
    }

    @Test
    void deveRetornar404QuandoAtualizarUmNovaUsuarioInexistente() {
        String jsonUsuario = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar.json");
        given()
            .pathParam("usuarioId", USUARIO_INEXISTENTE_ID)
            .body(jsonUsuario)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioComEmailExistente() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-email-existente.json");;
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioNomeVazio() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-nome-vazio.json");;
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioEmailVazio() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-email-vazio.json");;
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioEmailInvalido() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-email-invalido.json");;
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovaSenha() {
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body("{\"senhaAtual\":\"123\", \"novaSenha\":\"321\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaSenhaComSenhaAtualInvalida() {
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body("{\"senhaAtual\":\"invalida\", \"novaSenha\":\"321\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaSenhaComSenhaAtualVazia() {
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body("{\"senhaAtual\":\"  \", \"novaSenha\":\"321\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaSenhaComSenhaNovaVazia() {
        given()
            .pathParam("usuarioId", usuarioJoao.id())
            .body("{\"senhaAtual\":\"321\", \"novaSenha\":\"  \"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
