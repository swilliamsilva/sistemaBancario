package com.sistema.bancario.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableJpaRepositories(basePackages = "com.sistema.bancario.repository")
public class TestConfig {
    // Configurações adicionais de teste, se necessário
} 