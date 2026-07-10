/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.ufc.service;

import com.projeto.ufc.dto.LoginDTO;
import com.projeto.ufc.dto.UsuarioDTO;
import com.projeto.ufc.model.Usuario;
import com.projeto.ufc.repository.UsuarioRepository;
import com.projeto.ufc.security.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @author bruni
 */
@Service
public class AuthService {
    
    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository repository,
            PasswordEncoder encoder,
            JwtService jwtService) {

        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public Map<String, String> login(LoginDTO login) {

        logger.info("Tentativa de login do usuário {}", login.getUsername());

        Usuario usuario = repository.findByUsername(login.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(login.getPassword(), usuario.getPassword())) {

            logger.warn("Senha inválida para {}", login.getUsername());

            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.gerarToken(
                usuario.getUsername(),
                usuario.getRole());

        logger.info("Login realizado com sucesso para {}", usuario.getUsername());

        return Map.of("token", token);
    }

    public String register(UsuarioDTO dto) {

        logger.info("Cadastrando usuário {}", dto.getUsername());

        if (repository.findByUsername(dto.getUsername()).isPresent()) {

            throw new RuntimeException("Usuário já existente");
        }

        Usuario usuario = new Usuario();

        usuario.setUsername(dto.getUsername());
        usuario.setPassword(encoder.encode(dto.getPassword()));
        usuario.setRole(dto.getRole());

        repository.save(usuario);

        logger.info("Usuário {} cadastrado", dto.getUsername());

        return "Usuário cadastrado com sucesso";
    }
}
