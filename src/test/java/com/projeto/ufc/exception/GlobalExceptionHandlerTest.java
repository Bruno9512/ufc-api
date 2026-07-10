package com.projeto.ufc.exception;

import com.projeto.ufc.dto.ApiErrorDTO;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void deveTratarLutadorNaoEncontrado() {

        LutadorNotFoundException ex =
                new LutadorNotFoundException("Lutador não encontrado");

        ResponseEntity<ApiErrorDTO> response =
                handler.tratarLutadorNaoEncontrado(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(404, response.getBody().getStatus());
        assertEquals("Lutador não encontrado", response.getBody().getErro());
        assertEquals("Lutador não encontrado", response.getBody().getMensagem());
    }

    @Test
    void deveTratarErroDeValidacao() {

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "lutador");

        bindingResult.addError(
                new FieldError(
                        "lutador",
                        "nome",
                        "Nome é obrigatório"
                )
        );

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(
                        null,
                        bindingResult
                );

        ResponseEntity<Map<String, String>> response =
                handler.tratarValidacao(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(
                "Nome é obrigatório",
                response.getBody().get("nome")
        );
    }

    @Test
    void deveTratarAcessoNegado() {

        ResponseEntity<ApiErrorDTO> response =
                handler.tratarAcessoNegado(
                        new org.springframework.security.access.AccessDeniedException(
                                "Acesso negado"
                        )
                );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(403, response.getBody().getStatus());
        assertEquals("Acesso Negado", response.getBody().getErro());
        assertEquals(
                "Você não possui permissão para executar esta operação",
                response.getBody().getMensagem()
        );
    }

    @Test
    void deveTratarErroGenerico() {

        RuntimeException ex =
                new RuntimeException("Erro qualquer");

        ResponseEntity<ApiErrorDTO> response =
                handler.tratarErroGenerico(ex);

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(500, response.getBody().getStatus());
        assertEquals("Erro Interno", response.getBody().getErro());
        assertEquals("Erro qualquer", response.getBody().getMensagem());
    }
}