package com.food.api.v1.openapi.controller;

import com.food.api.exceptionhandler.Problem;
import com.food.api.v1.model.request.SenhaRequest;
import com.food.api.v1.model.request.UsuarioComSenhaRequest;
import com.food.api.v1.model.request.UsuarioSemSenhaRequest;
import com.food.api.v1.model.response.UsuarioResponse;
import com.food.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

import static com.food.config.OpenApiConfig.TAG_USUARIOS;

@SecurityRequirement(name = OpenApiConfig.SECURITY_AUTH)
@Tag(name = TAG_USUARIOS)
public interface UsuarioControllerOpenApi {

    @Operation(summary = "Lista os usuários")
    CollectionModel<UsuarioResponse> listar();

    @Operation(summary = "Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "ID do usuário inválido"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Usuário não encontrado")
    })
    UsuarioResponse porId(@Parameter(description = "ID do usuário", example = "1", required = true)
                           Long usuarioId);

    @Operation(summary = "Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    UsuarioResponse cadastrar(@Parameter( description = "Representação de um novo usuário", required = true)
                              UsuarioComSenhaRequest usuario);

    @Operation(summary = "Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Usuário não encontrado")
    })
    UsuarioResponse atualizar(@Parameter(description = "ID do usuário", example = "1", required = true)
                              Long usuarioId,
                              @Parameter( description = "Representação de um usuário com os novos dados",
                                      required = true)
                              UsuarioSemSenhaRequest usuario);

    @Operation(summary = "Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode  = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(ref = Problem.PROBLEMA)),  description = "Usuário não encontrado")
    })
    void alterarSenha(@Parameter(description = "ID do usuário", example = "1", required = true)
                      Long usuarioId,
                      @Parameter( description = "Representação de uma nova senha", required = true)
                      SenhaRequest senha);
}
