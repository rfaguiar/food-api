package com.food.api;

import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private CozinhaService cozinhaService;

    @Autowired
    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    @GetMapping
    public List<CozinhaDTO> listar() {
        return cozinhaService.todas();
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO portId(@PathVariable Long cozinhaId) {
        return cozinhaService.buscarPorId(cozinhaId);
    }
}
