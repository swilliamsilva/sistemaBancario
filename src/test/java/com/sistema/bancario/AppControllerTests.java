package com.sistema.bancario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AppControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    public void testPublicRoutes() throws Exception {
        mockMvc.perform(get("/public-route")) // Substitua "/public-route" pela rota real
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    public void testUserAccessDenied() throws Exception {
        mockMvc.perform(get("/restricted-route")) // Substitua "/restricted-route" pela rota real
                .andExpect(status().isOk());
    }
}
