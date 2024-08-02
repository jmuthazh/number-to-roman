package com.adobe.convertor.security;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Aug, 02 2024 - 3:34 AM
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    void testActuatorEndpointPermitted() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void testProtectedEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/romannumeral?query=100"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testProtectedEndpointWithAuthentication() throws Exception {
        mockMvc.perform(get("/romannumeral?query=100"))
                .andExpect(status().isOk());
    }
}
