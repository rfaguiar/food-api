package com.food.config;

import com.fasterxml.classmate.TypeResolver;
import com.food.api.exceptionhandler.Problem;
import com.food.api.model.response.CozinhaResponse;
import com.food.api.model.response.PedidoResponse;
import com.food.api.openapi.model.CozinhasModelOpenApi;
import com.food.api.openapi.model.PageableModelOpenApi;
import com.food.api.openapi.model.PedidosResumoModelOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.List;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class OpenApiConfig {

    public static final String TAG_CIDADE = "Cidades";
    public static final String TAG_GRUPO = "Grupos";
    public static final String TAG_COZINHA = "Cozinhas";
    public static final String TAG_FORMA_PAGAMENTO = "Formas de pagamento";
    public static final String TAG_PEDIDO = "Pedidos";
    public static final String TAG_RESTRAURANTE = "Restaurantes";
    public static final String TAG_ESTADOS = "Estados";
    public static final String TAG_PRODUTOS = "Produtos";

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.food.api"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, CozinhaResponse.class),
                        CozinhasModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResponse.class),
                        PedidosResumoModelOpenApi.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        File.class, Resource.class, InputStream.class, Sort.class)
                .apiInfo(apiInfo())
                .tags(createTag(TAG_CIDADE, "Gerencia as cidades"),
                        createTag(TAG_GRUPO, "Gerencia os grupos de usuários"),
                        createTag(TAG_COZINHA, "Gerencia as cozinhas"),
                        createTag(TAG_PEDIDO, "Gerencia os pedidos"),
                        createTag(TAG_RESTRAURANTE, "Gerencia os restaurantes"),
                        createTag(TAG_ESTADOS, "Gerencia os estados"),
                        createTag(TAG_PRODUTOS, "Gerencia os produtos de restaurantes"),
                        createTag(TAG_FORMA_PAGAMENTO, "Gerencia as formas de pagamento"));
    }

    private List<Response> globalDeleteResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno no servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .build()
        );
    }

    private List<Response> globalGetResponseMessages() {
        return List.of(
                new ResponseBuilder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .description("Erro interno do servidor")
                    .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Food API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Rogerio Aguiar", "https://github.com/rfaguiar", "rfaguiar1@gmail.com"))
                .build();
    }

    private Tag createTag(String name, String description) {
        return new Tag(name, description);
    }
}
