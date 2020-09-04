package com.food.api;

import com.food.service.RestauranteService;
import com.food.service.model.RestauranteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    @Autowired
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public List<RestauranteDto> listar() {
        return restauranteService.todos();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDto> porId(@PathVariable Long restauranteId) {
        return restauranteService.buscarPorId(restauranteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/por-nome-e-frete")
    public List<RestauranteDto> restaurantesPorNomeFrete(String nome,
                                                         BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteService.restaurantesPorNomeFrete(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @PostMapping
    public ResponseEntity<RestauranteDto> adicionar(@RequestBody RestauranteDto restaurante) {
        return restauranteService.adicionar(restaurante)
                .map(ResponseEntity.status(HttpStatus.CREATED)::body)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDto> atualizar(@PathVariable Long restauranteId,
                                                    @RequestBody RestauranteDto restaurante) {
        return restauranteService.atualizar(restauranteId, restaurante)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDto> atualizarParcial(@PathVariable Long restauranteId,
                                                           @RequestBody Map<String, Object> campos) {
        return restauranteService.atualizarParcial(restauranteId, campos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
