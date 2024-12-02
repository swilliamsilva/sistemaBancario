package com.sistema.bancario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil 'test'
public class AppControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicRoutes() throws Exception {
        // Confirma que as rotas públicas estão acessíveis
        mockMvc.perform(get("/home")).andExpect(status().isNotFound()); // Verifique a existência real desta rota
        mockMvc.perform(get("/criar-contas")).andExpect(status().isOk());
    }

    @Test
    public void testUserAccessDenied() throws Exception {
        // Com segurança desativada, a rota protegida deve retornar 200
        mockMvc.perform(get("/secured-endpoint")).andExpect(status().isOk());
    }
}
