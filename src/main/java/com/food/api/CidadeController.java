package com.food.api;

import com.food.service.CidadeService;
import com.food.service.model.CidadeDto;
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
@RequestMapping(value = "/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<CidadeDto> listar() {
        return cidadeService.todos();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeDto> porId(@PathVariable Long cidadeId) {
        return cidadeService.buscarPorId(cidadeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CidadeDto> adicionar(@RequestBody CidadeDto cidade) {
        return cidadeService.adicionar(cidade)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeDto> atualizar(@PathVariable Long cidadeId, @RequestBody CidadeDto cidade) {
        return cidadeService.atualizar(cidadeId, cidade)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Void> remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
        return ResponseEntity.noContent().build();
    }
}
