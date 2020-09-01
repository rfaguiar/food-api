package com.food.api;

import com.food.api.model.CozinhasXmlWrapper;
import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public List<CozinhaDTO> listar() {
        return cozinhaService.todas();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaService.todas());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDTO> portId(@PathVariable Long cozinhaId) {
        return cozinhaService.buscarPorId(cozinhaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CozinhaDTO> adicionar(@RequestBody CozinhaDTO cozinha) {
        return cozinhaService.salvar(cozinha)
                .map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaDTO> atualizar(@PathVariable Long cozinhaId,
                                                @RequestBody CozinhaDTO cozinha) {
        return cozinhaService.atualizar(cozinhaId, cozinha)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Object> remover(@PathVariable Long cozinhaId) {
        return cozinhaService.remover(cozinhaId)
                .map(e -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Void> dataIntegrityViolationHandler(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
