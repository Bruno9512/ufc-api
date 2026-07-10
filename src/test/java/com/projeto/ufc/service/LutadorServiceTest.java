/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.ufc.service;

import com.projeto.ufc.dto.LutadorDTO;
import com.projeto.ufc.exception.LutadorNotFoundException;
import com.projeto.ufc.model.Lutador;
import com.projeto.ufc.repository.LutadorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;



import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LutadorServiceTest {

    @Mock
    private LutadorRepository repository;

    @InjectMocks
    private LutadorService service;

    @Test
    void deveBuscarLutadorPorId() {

        Lutador lutador = new Lutador();

        lutador.setId(1L);
        lutador.setNome("Anderson Silva");
        lutador.setPais("Brasil");
        lutador.setCategoria("Peso Médio");
        lutador.setAltura(1.88);

        when(repository.findById(1L))
                .thenReturn(Optional.of(lutador));

        LutadorDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Anderson Silva", resultado.getNome());
        assertEquals("Brasil", resultado.getPais());
        assertEquals("Peso Médio", resultado.getCategoria());
        assertEquals(1.88, resultado.getAltura());

        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoLutadorNaoExistir() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                LutadorNotFoundException.class,
                () -> service.buscarPorId(99L)
        );

        verify(repository).findById(99L);
    }

    @Test
    void deveSalvarLutador() {

        LutadorDTO dto = new LutadorDTO();
        dto.setNome("Alex Pereira");
        dto.setPais("Brasil");
        dto.setCategoria("Peso Meio-Pesado");
        dto.setAltura(1.93);

        Lutador salvo = new Lutador();
        salvo.setId(1L);
        salvo.setNome(dto.getNome());
        salvo.setPais(dto.getPais());
        salvo.setCategoria(dto.getCategoria());
        salvo.setAltura(dto.getAltura());

        when(repository.save(any(Lutador.class)))
                .thenReturn(salvo);

        LutadorDTO resultado = service.salvar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Alex Pereira", resultado.getNome());
        assertEquals("Brasil", resultado.getPais());
        assertEquals("Peso Meio-Pesado", resultado.getCategoria());
        assertEquals(1.93, resultado.getAltura());

        verify(repository).save(any(Lutador.class));
    }

    @Test
    void deveAtualizarLutador() {

        Lutador existente = new Lutador();

        existente.setId(1L);
        existente.setNome("Anderson Silva");
        existente.setPais("Brasil");
        existente.setCategoria("Peso Médio");
        existente.setAltura(1.88);

        LutadorDTO dto = new LutadorDTO();

        dto.setNome("Anderson Spider Silva");
        dto.setPais("Brasil");
        dto.setCategoria("Peso Médio");
        dto.setAltura(1.88);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(any(Lutador.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LutadorDTO resultado = service.atualizar(1L, dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Anderson Spider Silva", resultado.getNome());
        assertEquals("Brasil", resultado.getPais());
        assertEquals("Peso Médio", resultado.getCategoria());
        assertEquals(1.88, resultado.getAltura());

        verify(repository).findById(1L);
        verify(repository).save(any(Lutador.class));
    }

    @Test
    void deveExcluirLutador() {

        Lutador lutador = new Lutador();

        lutador.setId(1L);
        lutador.setNome("Anderson Silva");

        when(repository.findById(1L))
                .thenReturn(Optional.of(lutador));

        service.excluir(1L);

        verify(repository).findById(1L);
        verify(repository).delete(lutador);
    }

    @Test
    void deveLancarExcecaoAoExcluirLutadorInexistente() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                LutadorNotFoundException.class,
                () -> service.excluir(99L)
        );

        verify(repository).findById(99L);

        verify(repository, never())
                .delete(any(Lutador.class));
    }
    
    @Test
    void deveListarTodos() {

        Lutador lutador = new Lutador();
        lutador.setId(1L);
        lutador.setNome("Anderson Silva");
        lutador.setPais("Brasil");
        lutador.setCategoria("Peso Médio");
        lutador.setAltura(1.88);

        Page<Lutador> pagina =
            new PageImpl<>(List.of(lutador));

        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable))
            .thenReturn(pagina);

        Page<LutadorDTO> resultado =
                service.listarTodos(pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Anderson Silva",
            resultado.getContent().getFirst().getNome());

        verify(repository).findAll(pageable);
 }
    
    @Test
    void deveBuscarPorNome() {

        Lutador lutador = new Lutador();

        lutador.setId(1L);
        lutador.setNome("Charles Oliveira");
        lutador.setPais("Brasil");
        lutador.setCategoria("Peso Leve");
        lutador.setAltura(1.78);

        when(repository.findByNomeContainingIgnoreCase("Charles"))
            .thenReturn(List.of(lutador));

        List<LutadorDTO> resultado =
            service.buscarPorNome("Charles");

        assertEquals(1, resultado.size());
        assertEquals("Charles Oliveira",
            resultado.getFirst().getNome());

        verify(repository)
            .findByNomeContainingIgnoreCase("Charles");
 }
    
    @Test
    void deveBuscarPorPais() {

        Lutador lutador = new Lutador();

        lutador.setId(4L);
        lutador.setNome("Alex Pereira");
        lutador.setPais("Brasil");
        lutador.setCategoria("Peso Meio-Pesado");
        lutador.setAltura(1.93);

        when(repository.findByPaisIgnoreCase("Brasil"))
            .thenReturn(List.of(lutador));

        List<LutadorDTO> resultado =
            service.buscarPorPais("Brasil");

        assertEquals(1, resultado.size());
        assertEquals("Brasil",
            resultado.getFirst().getPais());

        verify(repository)
            .findByPaisIgnoreCase("Brasil");
 }
    
    @Test
    void deveBuscarPorCategoria() {

        Lutador lutador = new Lutador();

        lutador.setId(6L);
        lutador.setNome("Jon Jones");
        lutador.setPais("EUA");
        lutador.setCategoria("Peso Pesado");
        lutador.setAltura(1.95);

        when(repository.findByCategoriaIgnoreCase("Peso Pesado"))
            .thenReturn(List.of(lutador));

        List<LutadorDTO> resultado =
            service.buscarPorCategoria("Peso Pesado");

        assertEquals(1, resultado.size());
        assertEquals("Peso Pesado",
            resultado.getFirst().getCategoria());

        verify(repository)
            .findByCategoriaIgnoreCase("Peso Pesado");
 }
    
    @Test
    void deveBuscarMaisAltosQue() {

        Lutador lutador = new Lutador();

        lutador.setId(6L);
        lutador.setNome("Jon Jones");
        lutador.setPais("EUA");
        lutador.setCategoria("Peso Pesado");
        lutador.setAltura(1.95);

        when(repository.buscarMaisAltosQue(1.90))
            .thenReturn(List.of(lutador));

        List<LutadorDTO> resultado =
            service.buscarMaisAltosQue(1.90);

        assertEquals(1, resultado.size());
        assertEquals("Jon Jones",
            resultado.getFirst().getNome());

        verify(repository)
            .buscarMaisAltosQue(1.90);
 }
}