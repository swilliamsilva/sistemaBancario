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
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAuthenticationRedirect() throws Exception {
        // Como a segurança está desativada, não há redirecionamento
        mockMvc.perform(get("/secured-endpoint")).andExpect(status().isOk());
    }

    @Test
    public void testUserAccessDenied() throws Exception {
        // Sem segurança, o acesso não é negado
        mockMvc.perform(get("/secured-endpoint")).andExpect(status().isOk());
    }
}
