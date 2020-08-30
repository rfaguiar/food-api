package com.food.jpa;

import com.food.Application;
import com.food.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaFormaPagamentoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository pagamentos = applicationContext.getBean(FormaPagamentoRepository.class);
        pagamentos.todas()
            .forEach(System.out::println);
    }
}
