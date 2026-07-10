package com.projeto.ufc.exception;

import com.projeto.ufc.dto.ApiErrorDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LutadorNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> tratarLutadorNaoEncontrado(
        LutadorNotFoundException ex) {

        ApiErrorDTO erro = new ApiErrorDTO(
                404,
                "Lutador não encontrado",
                ex.getMessage()
        );

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(erro);
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(erro ->
                        erros.put(
                                erro.getField(),
                                erro.getDefaultMessage()
                        )
                );

        return ResponseEntity.badRequest().body(erros);
    }
    
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiErrorDTO> tratarAcessoNegado(Exception ex){
        
        ApiErrorDTO erro = new ApiErrorDTO(
            403,
            "Acesso Negado",
            "Você não possui permissão para executar esta operação"
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(erro);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorDTO> tratarErroGenerico(RuntimeException ex){
        
        ApiErrorDTO erro = new ApiErrorDTO(
                500,
                "Erro Interno",
                ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }
}