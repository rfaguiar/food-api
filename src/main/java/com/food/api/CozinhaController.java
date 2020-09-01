package com.food.api;

import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaService cozinhaService;

    @Autowired
    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @GetMapping
    public List<CozinhaDto> listar() {
        return cozinhaService.todas();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDto> portId(@PathVariable Long cozinhaId) {
        return cozinhaService.buscarPorId(cozinhaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CozinhaDto> adicionar(@RequestBody CozinhaDto cozinha) {
        return cozinhaService.salvar(cozinha)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDto> atualizar(@PathVariable Long cozinhaId,
                                                @RequestBody CozinhaDto cozinha) {
        return cozinhaService.atualizar(cozinhaId, cozinha)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Void> remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
        return ResponseEntity.noContent().build();
    }
}
