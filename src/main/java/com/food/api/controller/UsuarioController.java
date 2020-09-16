package com.food.api.controller;

import com.food.api.model.request.SenhaRequest;
import com.food.api.model.request.UsuarioComSenhaRequest;
import com.food.api.model.request.UsuarioSemSenhaRequest;
import com.food.api.model.response.UsuarioResponse;
import com.food.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{usuarioId}")
    public UsuarioResponse buscar(@PathVariable Long usuarioId) {
        return usuarioService.buscar(usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@RequestBody @Valid UsuarioComSenhaRequest usuario) {
        return usuarioService.cadastrar(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioResponse atualizar(@PathVariable Long usuarioId,
                                     @RequestBody @Valid UsuarioSemSenhaRequest usuario) {
        return usuarioService.atualizar(usuarioId, usuario);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaRequest senha) {
        usuarioService.alterarSenha(usuarioId, senha.senhaAtual(), senha.novaSenha());
    }
}