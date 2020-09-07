package com.food;

import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.service.CozinhaService;
import com.food.service.model.CozinhaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CadastroCozinhaIT {

    @Autowired
    private CozinhaService cozinhaService;

    @Test
    void testarCadastroCozinhaComSucesso() {
        //cénario
        var novaCozinha = new CozinhaDto(null, "Chinesa");

        //Ação
        novaCozinha = cozinhaService.salvar(novaCozinha);

        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.id()).isNotNull();
    }

    @Test
    void deveFalharAoCadastrarCozinhaQuandoSemNome() {
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            var novaCozinha = new CozinhaDto(null, null);
            cozinhaService.salvar(novaCozinha);
        });
        assertThat(exception).isNotNull();
    }

    @Test
    void deveFalharQuandoExcluirCozinhaEmUso() {
        Exception exception = assertThrows(EntidadeEmUsoException.class,
                () -> cozinhaService.remover(1L));
        assertThat(exception).isNotNull();
    }

    @Test
    void deveFalharQuandoExcluirCozinhaInexistente() {
        Exception exception = assertThrows(CozinhaNaoEncontradaException.class,
                () -> cozinhaService.remover(100L));
        assertThat(exception).isNotNull();
    }

}
