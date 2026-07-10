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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author bruni
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock
    private UsuarioRepository repository;
    
    @Mock
    private PasswordEncoder encoder;
    
    @Mock
    private JwtService jwtService;
    
    @InjectMocks
    private AuthService service;
    
    @Test
    void deveRealizarLogin(){
        
        LoginDTO login = new LoginDTO();
        
        login.setUsername("admin");
        login.setPassword("123");
        
        Usuario usuario = new Usuario();
        
        usuario.setUsername("admin");
        usuario.setPassword("senhaCriptografada");
        usuario.setRole("ROLE_ADMIN");
        
        when(repository.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));
        
        when(encoder.matches(
                "123",
                "senhaCriptografada"))
                .thenReturn(true);
        
        when(jwtService.gerarToken("admin", "ROLE_ADMIN"))
                .thenReturn("TOKEN123");
        
        Map<String, String> resultado = service.login(login);
        
        assertEquals("TOKEN123", resultado.get("token"));
        
        verify(repository).findByUsername("admin");
        verify(encoder)
                .matches("123", "senhaCriptografada");
        verify(jwtService).gerarToken("admin", "ROLE_ADMIN");
    }
    
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste(){
        
        LoginDTO login = new LoginDTO();
        
        login.setUsername("admin");
        login.setPassword("123");
        
        when(repository.findByUsername("admin"))
                .thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(
                                     RuntimeException.class,
                                                    () -> service.login(login));
        
        assertEquals("Usuário não encontrado",
                exception.getMessage());
        
        verify(repository).findByUsername("admin");
        
        verifyNoInteractions(encoder, jwtService);
    }
    
    @Test
    void deveLancarExcecaoQuandoSenhaForInvalida() {

        LoginDTO login = new LoginDTO();

        login.setUsername("admin");
        login.setPassword("123");

        Usuario usuario = new Usuario();

        usuario.setUsername("admin");
        usuario.setPassword("senhaCriptografada");
        usuario.setRole("ROLE_ADMIN");

        when(repository.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        when(encoder.matches(
            "123",
            "senhaCriptografada"))
            .thenReturn(false);

        RuntimeException exception =
                assertThrows(
                    RuntimeException.class,
                    () -> service.login(login)
            );

        assertEquals(
            "Senha inválida",
            exception.getMessage());

        verify(repository).findByUsername("admin");
        verify(encoder)
                .matches("123", "senhaCriptografada");

        verifyNoInteractions(jwtService);
    }
    
    @Test
    void deveCadastrarUsuario() {

        UsuarioDTO dto = new UsuarioDTO();

        dto.setUsername("joao");
        dto.setPassword("123");
        dto.setRole("ROLE_USER");

        when(repository.findByUsername("joao"))
            .thenReturn(Optional.empty());

        when(encoder.encode("123"))
            .thenReturn("senhaCriptografada");

        String resultado =
            service.register(dto);

        assertEquals(
            "Usuário cadastrado com sucesso",
            resultado);

        verify(repository).save(any(Usuario.class));
    }
    
    @Test
    void deveLancarExcecaoQuandoUsuarioJaExistir() {

        UsuarioDTO dto = new UsuarioDTO();

        dto.setUsername("admin");
        dto.setPassword("123");

        Usuario usuario = new Usuario();

        usuario.setUsername("admin");

        when(repository.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        RuntimeException exception =
                assertThrows(
                    RuntimeException.class,
                    () -> service.register(dto)
            );

        assertEquals(
            "Usuário já existente",
            exception.getMessage());

        verify(repository).findByUsername("admin");

        verify(repository, never())
            .save(any());

        verifyNoInteractions(
            encoder,
            jwtService);
    }
    
    
    
}
