package com.food.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

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
/*
    @Bean
    public Docket apiDocketV1() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.food.api"))
                    .paths(PathSelectors.ant("/v1/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(PagedModel.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaResponse.class),
                        CozinhasModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeResponse.class),
                        CidadesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResponse.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoResponse.class),
                        EstadosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoResponse.class),
                        FormasPagamentoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoResponse.class),
                        GruposModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoResponse.class),
                        PermissoesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoResponse.class),
                        ProdutosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoResponse.class),
                        RestaurantesBasicoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioResponse.class),
                        UsuariosModelOpenApi.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        File.class, Resource.class, InputStream.class, Sort.class)
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfoV1())
                .tags(createTag(TAG_CIDADE, "Gerencia as cidades"),
                        createTag(TAG_GRUPO, "Gerencia os grupos de usuários"),
                        createTag(TAG_COZINHA, "Gerencia as cozinhas"),
                        createTag(TAG_PEDIDO, "Gerencia os pedidos"),
                        createTag(TAG_RESTRAURANTE, "Gerencia os restaurantes"),
                        createTag(TAG_ESTADOS, "Gerencia os estados"),
                        createTag(TAG_PRODUTOS, "Gerencia os produtos de restaurantes"),
                        createTag(TAG_USUARIOS, "Gerencia os usuários"),
                        createTag(TAG_ESTATISTICAS, "Estatísticas da AlgaFood"),
                        createTag(TAG_PERMISSOES, "Gerencia as permissões"),
                        createTag(TAG_FORMA_PAGAMENTO, "Gerencia as formas de pagamento"));
    }

    @Bean
    public Docket apiDocketV2() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.food.api"))
                    .paths(PathSelectors.ant("/v2/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(PagedModel.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaResponseV2.class),
                        CozinhasModelV2OpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeResponseV2.class),
                        CidadesModelV2OpenApi.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        File.class, Resource.class, InputStream.class, Sort.class)
                .apiInfo(apiInfoV2())
                .tags(new Tag(TAG_CIDADE, "Gerencia as cidades"),
                        new Tag(TAG_COZINHA, "Gerencia as cozinhas"));

    }

    @Bean
    public Docket apiDocketV3() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V3")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.food.api"))
                .paths(PathSelectors.ant("/v3/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(PagedModel.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaResponseV3.class),
                        CozinhasModelV3OpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeResponseV3.class),
                        CidadesModelV3OpenApi.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        File.class, Resource.class, InputStream.class, Sort.class)
                .apiInfo(apiInfoV3())
                .tags(new Tag(TAG_CIDADE, "Gerencia as cidades"),
                        new Tag(TAG_COZINHA, "Gerencia as cozinhas"));

    }

    private SecurityContext securityContext() {
        var securityReference = SecurityReference.builder()
                .reference("FoodOAuth")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();
        return SecurityContext.builder()
                .securityReferences(List.of(securityReference))
                .forPaths(PathSelectors.any())
                .build();
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("FoodOAuth")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    private List<AuthorizationScope> scopes() {
        return List.of(
                new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita")
        );
    }

    private List<GrantType> grantTypes() {
        return List.of(
                new ResourceOwnerPasswordCredentialsGrant("/oauth/token")
        );
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

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("Food API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Rogerio Aguiar", "https://github.com/rfaguiar", "rfaguiar1@gmail.com"))
                .build();
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("Food API")
                .description("API aberta para clientes e restaurantes<br>" +
                        "<strong>Essa versão da API está depreciada e deixará de existir a partir de 01/01/2021.<br>" +
                        "Use a versão mais atual da API.</strong>")
                .version("2")
                .contact(new Contact("Rogerio Aguiar", "https://github.com/rfaguiar", "rfaguiar1@gmail.com"))
                .build();
    }

    private ApiInfo apiInfoV3() {
        return new ApiInfoBuilder()
                .title("Food API")
                .description("API aberta para clientes e restaurantes<br>" +
                        "<strong>Essa versão da API deixou de existir em 31/10/2020.</strong>")
                .version("3")
                .contact(new Contact("Rogerio Aguiar", "https://github.com/rfaguiar", "rfaguiar1@gmail.com"))
                .build();
    }

    private Tag createTag(String name, String description) {
        return new Tag(name, description);
    }
    */
}
