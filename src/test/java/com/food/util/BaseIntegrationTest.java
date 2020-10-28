package com.food.util;

import com.food.domain.model.Grupo;
import com.food.domain.model.Permissao;
import com.food.domain.model.Usuario;
import com.food.domain.repository.GrupoRepository;
import com.food.domain.repository.PermissaoRepository;
import com.food.domain.repository.UsuarioRepository;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;
    @Autowired
    protected DatabaseCleaner databaseCleaner;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private PermissaoRepository permissaoRepository;

    protected String emitirTokenComPermissaoGerente() {
        return given()
                .header("Authorization", "Basic Zm9vZC13ZWI6d2ViMTIz")
                .body("username=manoel.gerente@gmail.com&password=123&grant_type=password")
                .contentType(ContentType.URLENC)
                .accept(ContentType.JSON)
                .when()
                .post("/oauth/token")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .get("access_token");
    }

    protected void configurarServer() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.port = port;
        databaseCleaner.clearTables();
        prepararOAuthUsers();
    }

    private void prepararOAuthUsers() {
        String oAuthclient = """
        insert into oauth_client_details (
                client_id, resource_ids, client_secret,
                scope, authorized_grant_types, web_server_redirect_uri, authorities,
                access_token_validity, refresh_token_validity, autoapprove
        )
        values (
                'food-web', null, '$2y$12$Qhmqs9S0NAZBMODrqb/LtOF2toueNWtdhgGizWZHbeasGhS7higfO',
                'READ,WRITE', 'password,refresh_token', null, null,
                60 * 60 * 6, 60 * 24 * 60 * 60, null
        );
                """;
        jdbcTemplate.execute(oAuthclient);

        List<Permissao> permissoes = permissaoRepository.saveAll(List.of(
                new Permissao(null, "EDITAR_COZINHAS", "Permite editar cozinhas"),
                new Permissao(null, "EDITAR_FORMAS_PAGAMENTO", "Permite criar ou editar formas de pagamento"),
                new Permissao(null, "EDITAR_CIDADES", "Permite criar ou editar cidades"),
                new Permissao(null, "EDITAR_ESTADOS", "Permite criar ou editar estados"),
                new Permissao(null, "CONSULTAR_USUARIOS_GRUPOS_PERMISSOES", "Permite consultar usuários"),
                new Permissao(null, "EDITAR_USUARIOS_GRUPOS_PERMISSOES", "Permite criar ou editar usuários"),
                new Permissao(null, "EDITAR_RESTAURANTES", "Permite criar, editar ou gerenciar restaurantes"),
                new Permissao(null, "CONSULTAR_PEDIDOS", "Permite consultar pedidos"),
                new Permissao(null, "GERENCIAR_PEDIDOS", "Permite gerenciar pedidos"),
                new Permissao(null, "GERAR_RELATORIOS", "Permite gerar relatórios")
        ));
        Grupo grupoGerente = grupoRepository.save(new Grupo(null, "Gerente", Set.copyOf(permissoes)));
        usuarioRepository.save(new Usuario(null, "Manoel Lima",
                "manoel.gerente@gmail.com", "$2y$12$GlQ0PAx8LnZQQwLJV8CzkuZCm8LRO0f/OknfpLATtpignJ0IEA9bS",
                null, Set.of(grupoGerente)));
    }
}
