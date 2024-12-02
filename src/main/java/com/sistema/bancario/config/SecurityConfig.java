package com.sistema.bancario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @Profile("prod") // Ativado somente no ambiente de produção
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/imagens/**").permitAll()
                .antMatchers("/administracao/**").hasRole("ADMIN")
                .antMatchers("/", "/home", "/criar-contas", "/listar-contas", "/transferir").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/administracao")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
        return http.build();
    }

    @Bean
    @Profile({"dev", "test"}) // Permite apenas nos perfis dev e test
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().permitAll() // Permite todas as requisições
            .and().csrf().disable(); // Desativa o CSRF
        return http.build();
    }



    
}

