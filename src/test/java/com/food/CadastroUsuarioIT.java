package com.food;

import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;
import com.food.util.BaseIntegrationTest;
import com.food.util.ResourceUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class CadastroUsuarioIT extends BaseIntegrationTest {

    private static final int USUARIO_INEXISTENTE_ID = 100;
    private int quantidadeUsuariosCadastrados;
    private Usuario usuarioJoao;
    private final String basePathUsuario = "/v1/usuarios";
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void begin() {
        super.configurarServer();
        prepararDados();
    }

    private void prepararDados() {
        usuarioRepository.saveAll(List.of(
                new Usuario(null, "Maria Joaquina", "maria.vnd@food.com", passwordEncoder.encode("123"), null, null),
                new Usuario(null, "José Souza", "jose.aux@food.com", passwordEncoder.encode("123"), null, null)));
        usuarioJoao = usuarioRepository.save(new Usuario(null, "João da Silva", "joao.ger@food.com", passwordEncoder.encode("123"), null, null));
        quantidadeUsuariosCadastrados = (int) usuarioRepository.count();
    }

    @Test
    void deveRetornar200QuandoConsultarUsuarios() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
            .when()
        .get(basePathUsuario)
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterUsuarioQuandoConsultar() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .accept(ContentType.JSON)
        .when()
            .get(basePathUsuario)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("_embedded.usuarios", hasSize(quantidadeUsuariosCadastrados))
            .body("_embedded.usuarios.nome", hasItems("Maria Joaquina", "José Souza", "João da Silva"));
    }

    @Test
    void deveRetornar200QuandoConsultarUmUsuario() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .accept(ContentType.JSON)
        .when()
            .get(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveConterUsuarioQuandoConsultarPassandooId() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .accept(ContentType.JSON)
        .when()
            .get(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(usuarioJoao.getId().intValue()))
            .body("nome", equalTo(usuarioJoao.getNome()))
            .body("email", equalTo(usuarioJoao.getEmail()));
    }

    @Test
    void deveRetornar404QuandoConsultarUmaUsuarioInexistente() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", USUARIO_INEXISTENTE_ID)
            .accept(ContentType.JSON)
        .when()
            .get(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar204QuandoCadastrarUmNovoUsuario() {
        String jsonUsuario = ResourceUtils.getContentFromResource(
                "/json/correto/usuario.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonUsuario)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathUsuario)
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", notNullValue())
            .body("nome", equalTo("Daniel"))
            .body("email", equalTo("dani@food.com"));
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComEmailExistente() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-email-existente.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathUsuario)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComNomeVazio() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-nome-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathUsuario)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComSenhaVazio() {
        String jsonUsuarioSenhaVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-senha-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonUsuarioSenhaVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathUsuario)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComEmailVazio() {
        String jsonUsuarioEmailVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-email-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonUsuarioEmailVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathUsuario)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoCadastrarUmNovoUsuarioComEmailInvalido() {
        String jsonUsuarioEmailInvalidoVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-email-invalido.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .body(jsonUsuarioEmailInvalidoVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post(basePathUsuario)
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovoUsuario() {
        String jsonUsuario = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body(jsonUsuario)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(usuarioJoao.getId().intValue()))
            .body("nome", equalTo("Daniel"))
            .body("email", equalTo("dani@food.com"));
    }

    @Test
    void deveRetornar404QuandoAtualizarUmNovaUsuarioInexistente() {
        String jsonUsuario = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", USUARIO_INEXISTENTE_ID)
            .body(jsonUsuario)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioComEmailExistente() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-email-existente.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioNomeVazio() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-nome-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioEmailVazio() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-email-vazio.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaUsuarioEmailInvalido() {
        String jsonUsuarioNomeVazio = ResourceUtils.getContentFromResource(
                "/json/correto/usuario-atualizar-email-invalido.json");
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body(jsonUsuarioNomeVazio)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar200QuandoAtualizarUmNovaSenha() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body("{\"senhaAtual\":\"123\", \"novaSenha\":\"321\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaSenhaComSenhaAtualInvalida() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body("{\"senhaAtual\":\"invalida\", \"novaSenha\":\"321\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaSenhaComSenhaAtualVazia() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body("{\"senhaAtual\":\"  \", \"novaSenha\":\"321\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornar400QuandoAtualizarUmNovaSenhaComSenhaNovaVazia() {
        String accessToken = emitirTokenComPermissaoGerente();
        given()
            .auth().oauth2(accessToken)
            .pathParam("usuarioId", usuarioJoao.getId())
            .body("{\"senhaAtual\":\"321\", \"novaSenha\":\"  \"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put(basePathUsuario + "/{usuarioId}/senha")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
