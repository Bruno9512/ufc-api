package com.projeto.ufc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.ufc.dto.LoginDTO;
import com.projeto.ufc.dto.UsuarioDTO;
import com.projeto.ufc.security.JwtAuthenticationFilter;
import com.projeto.ufc.security.JwtService;
import com.projeto.ufc.service.AuthService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.security.authentication.TestingAuthenticationToken;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void contextoCarrega() {
    }

    @Test
    void deveRealizarLogin() throws Exception {

        LoginDTO dto = new LoginDTO();
        dto.setUsername("admin");
        dto.setPassword("123");

        when(authService.login(any(LoginDTO.class)))
                .thenReturn(Map.of("token", "TOKEN_TEST"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token")
                        .value("TOKEN_TEST"));
    }

    @Test
    void deveRegistrarUsuario() throws Exception {

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsername("joao");
        dto.setPassword("123456");
        dto.setRole("ROLE_USER");

        when(authService.register(any(UsuarioDTO.class)))
                .thenReturn("Usuário cadastrado com sucesso");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarUsuarioAutenticado() throws Exception {

        TestingAuthenticationToken authentication =
                new TestingAuthenticationToken(
                        "admin",
                        null,
                        "ROLE_ADMIN");

        mockMvc.perform(get("/auth/me")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username")
                        .value("admin"))
                .andExpect(jsonPath("$.role")
                        .value("ROLE_ADMIN"));
    }
}