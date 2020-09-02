package com.food.api;

import com.food.service.EstadoService;
import com.food.service.model.EstadoDto;
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
@RequestMapping(value = "/estados")
public class EstadoController {

    private final EstadoService estadoService;

    @Autowired
    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public List<EstadoDto> listar() {
        return estadoService.todos();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoDto> porId(@PathVariable Long estadoId) {
        return estadoService.buscarPorId(estadoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstadoDto> adicionar(@RequestBody EstadoDto estado) {
        return estadoService.adicionar(estado)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoDto> atualizar(@PathVariable Long estadoId, @RequestBody EstadoDto estado) {
        return estadoService.atualizar(estadoId, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Void> remover(@PathVariable Long estadoId) {
        estadoService.remover(estadoId);
        return ResponseEntity.noContent().build();
    }
}
