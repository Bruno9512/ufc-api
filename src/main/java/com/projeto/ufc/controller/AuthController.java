package com.projeto.ufc.controller;

import com.projeto.ufc.dto.LoginDTO;
import com.projeto.ufc.dto.UsuarioDTO;
import com.projeto.ufc.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@Tag(
    name = "Autenticação",
    description = "Endpoints de autenticação"
)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }
    
    @Operation(summary = "Realizar login")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO login) {

        logger.info("Requisição de login.");

        return ResponseEntity.ok(
                service.login(login)
        );
    }
    
    @Operation(summary = "Cadastrar usuário")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioDTO dto) {

        logger.info("Requisição de cadastro.");

        return ResponseEntity.ok(
                service.register(dto)
        );
    }
    
    @Operation(summary = "Retorna o usuário autenticado")
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        String role = authentication.getAuthorities()
            .iterator()
            .next()
            .getAuthority();

        return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "role", role
                )
        );
    }
}