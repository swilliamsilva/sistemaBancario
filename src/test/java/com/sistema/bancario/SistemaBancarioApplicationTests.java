package com.sistema.bancario;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.sistema.bancario.service.ServicoConta;
import com.sistema.bancario.repository.ContaRepository;

@SpringBootTest
public class SistemaBancarioApplicationTests {
    
    @Autowired
    private ApplicationContext context;
    
    @Test
    void contextLoads() {
        assertNotNull(context);
    }

    @Test
    void verificaComponentesEssenciais() {
        assertNotNull(context.getBean(ServicoConta.class));
        assertNotNull(context.getBean(ContaRepository.class));
    }
} 