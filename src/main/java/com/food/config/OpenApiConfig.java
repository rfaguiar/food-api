package com.food.config;

import com.food.api.exceptionhandler.Problem;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Configuration
@SecurityScheme(name = OpenApiConfig.SECURITY_AUTH,
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
public class OpenApiConfig {

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";
    public static final String SECURITY_AUTH = "security_auth";
    public static final String TAG_CIDADE = "Cidades";
    public static final String TAG_GRUPO = "Grupos";
    public static final String TAG_COZINHA = "Cozinhas";
    public static final String TAG_FORMA_PAGAMENTO = "Formas de pagamento";
    public static final String TAG_PEDIDO = "Pedidos";
    public static final String TAG_RESTRAURANTE = "Restaurantes";
    public static final String TAG_ESTADOS = "Estados";
    public static final String TAG_PRODUTOS = "Produtos";
    public static final String TAG_USUARIOS = "Usuários";
    public static final String TAG_ESTATISTICAS = "Estatísticas";
    public static final String TAG_PERMISSOES = "Permissões";

    @Bean
    public GroupedOpenApi groupedOpenApiV1() {
        return GroupedOpenApi.builder()
                .group("Food API V1")
                .pathsToMatch("/v1/**")
                .addOpenApiCustomiser(openApi -> {
                    openApi.info(new Info()
                            .title("Food API V1")
                            .version("v1")
                            .description("API aberta para clientes e restaurantes")
                            .license(new License()
                                    .name("Apache 2.0")
                                    .url("http://springdoc.com")
                            )
                    ).externalDocs(new ExternalDocumentation()
                            .description("Rogerio Aguiar")
                            .url("https://github.com/rfaguiar")
                    ).tags(List.of(
                            new Tag().name(TAG_CIDADE).description("Gerencia as cidades"),
                            new Tag().name(TAG_GRUPO).description("Gerencia os grupos de usuários"),
                            new Tag().name(TAG_COZINHA).description("Gerencia as cozinhas"),
                            new Tag().name(TAG_PEDIDO).description("Gerencia os pedidos"),
                            new Tag().name(TAG_RESTRAURANTE).description("Gerencia os restaurantes"),
                            new Tag().name(TAG_ESTADOS).description("Gerencia os estados"),
                            new Tag().name(TAG_PRODUTOS).description("Gerencia os produtos de restaurantes"),
                            new Tag().name(TAG_USUARIOS).description("Gerencia os usuários"),
                            new Tag().name(TAG_ESTATISTICAS).description("Estatísticas da AlgaFood"),
                            new Tag().name(TAG_PERMISSOES).description("Gerencia as permissões"),
                            new Tag().name(TAG_FORMA_PAGAMENTO).description("Gerencia as formas de pagamento")
                    )).components(new Components().schemas(
                            gerarSchemas()
                    ));
                })
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV2() {
        return GroupedOpenApi.builder()
                .group("Food API V2")
                .pathsToMatch("/v2/**")
                .addOpenApiCustomiser(openApi -> {
                    openApi.info(new Info()
                            .title("Food API V2")
                            .version("v2")
                            .description("API aberta para clientes e restaurantes")
                            .license(new License()
                                    .name("Apache 2.0")
                                    .url("http://springdoc.com")
                            )
                    ).externalDocs(new ExternalDocumentation()
                            .description("Rogerio Aguiar")
                            .url("https://github.com/rfaguiar")
                    ).components(new Components().schemas(
                            gerarSchemas()
                    ));
                })
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV3() {
        return GroupedOpenApi.builder()
                .group("Food API V3")
                .pathsToMatch("/api/v3/**")
                .addOpenApiCustomiser(openApi -> {
                    openApi.info(new Info()
                            .title("Food API V3")
                            .version("v3")
                            .description("API aberta para clientes e restaurantes")
                            .license(new License()
                                    .name("Apache 2.0")
                                    .url("http://springdoc.com")
                            )
                    ).externalDocs(new ExternalDocumentation()
                            .description("Rogerio Aguiar")
                            .url("https://github.com/rfaguiar")
                    ).components(new Components().schemas(
                            gerarSchemas()
                    ));
                })
                .build();
    }
    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                switch (httpMethod) {
                                    case GET:
                                        responses.addApiResponse("406", new ApiResponse().$ref(notAcceptableResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case POST:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case PUT:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case DELETE:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    default:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                }
                            })
                    );
        };
    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }
    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        return apiResponseMap;
    }

}
