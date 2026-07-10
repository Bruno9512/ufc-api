package com.projeto.ufc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.ufc.dto.LutadorDTO;
import com.projeto.ufc.security.JwtAuthenticationFilter;
import com.projeto.ufc.security.JwtService;
import com.projeto.ufc.service.LutadorService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LutadorController.class)
@AutoConfigureMockMvc(addFilters = false)
class LutadorControllerTest {


@Autowired
private MockMvc mockMvc;

@Autowired
private ObjectMapper objectMapper;

@MockBean
private LutadorService service;

@MockBean
private JwtService jwtService;

@MockBean
private JwtAuthenticationFilter jwtAuthenticationFilter;



@Test
void deveListarLutadores() throws Exception {

    LutadorDTO dto = new LutadorDTO();
    dto.setId(1L);
    dto.setNome("Anderson Silva");
    dto.setPais("Brasil");
    dto.setCategoria("Peso Médio");
    dto.setAltura(1.88);

    Page<LutadorDTO> pagina =
            new PageImpl<>(List.of(dto));

    when(service.listarTodos(any()))
            .thenReturn(pagina);

    mockMvc.perform(get("/lutadores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].nome")
                    .value("Anderson Silva"));
}

@Test
void deveBuscarPorId() throws Exception {

    LutadorDTO dto = new LutadorDTO();
    dto.setId(1L);
    dto.setNome("Anderson Silva");

    when(service.buscarPorId(1L))
            .thenReturn(dto);

    mockMvc.perform(get("/lutadores/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome")
                    .value("Anderson Silva"));
}

@Test
void deveSalvarLutador() throws Exception {

    LutadorDTO dto = new LutadorDTO();
    dto.setId(1L);
    dto.setNome("Anderson Silva");
    dto.setPais("Brasil");
    dto.setCategoria("Peso Médio");
    dto.setAltura(1.88);

    when(service.salvar(any(LutadorDTO.class)))
            .thenReturn(dto);

    mockMvc.perform(post("/lutadores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nome")
                    .value("Anderson Silva"));
}

@Test
void deveExcluirLutador() throws Exception {

    doNothing().when(service)
            .excluir(anyLong());

    mockMvc.perform(delete("/lutadores/1"))
            .andExpect(status().isNoContent());
}

@Test
void devebBuscarLutador() throws Exception{
    
    LutadorDTO dto = new LutadorDTO();
    dto.setId(1L);
    dto.setNome("Anderson Silva");
    
    when(service.buscarPorNome("Anderson"))
            .thenReturn(List.of(dto));
    
    mockMvc.perform(get("/lutadores/buscar")
                    .param("nome", "Anderson"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome")
                    .value("Anderson Silva"));
}

@Test
void deveBuscarPorPais() throws Exception{
    
    LutadorDTO dto = new LutadorDTO();
    dto.setPais("Brasil");
    
    when(service.buscarPorPais("Brasil"))
            .thenReturn(List.of(dto));
    
    mockMvc.perform(get("/lutadores/pais/Brasil"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].pais")
                    .value("Brasil"));
}

@Test
void deveBuscarMaisAltosQue() throws Exception{
    
    LutadorDTO dto = new LutadorDTO();
    dto.setAltura(1.93);
    
    when(service.buscarMaisAltosQue(1.90))
            .thenReturn(List.of(dto));
    
    mockMvc.perform(get("/lutadores/altura")
                    .param("altura", "1.90"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].altura")
                    .value(1.93));
}

@Test
void deveBuscarPorCategoria() throws Exception{
    
    LutadorDTO dto = new LutadorDTO();
    dto.setCategoria("Peso Médio");
    
    when(service.buscarPorCategoria("Peso Médio"))
            .thenReturn(List.of(dto));
    
    mockMvc.perform(get("/lutadores/categoria")
                    .param("categoria", "Peso Médio"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].categoria")
                    .value("Peso Médio"));
}

@Test
void deveAtualizarLutador() throws Exception{
    
    LutadorDTO dto = new LutadorDTO();
    
    dto.setId(1L);
    dto.setNome("Anderson Silva");
    dto.setPais("Brasil");
    dto.setCategoria("Peso Médio");
    dto.setAltura(1.88);
    
    when(service.atualizar(anyLong(), any(LutadorDTO.class)))
            .thenReturn(dto);
    
    mockMvc.perform(put("/lutadores/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome")
                    .value("Anderson Silva"));
}


}
