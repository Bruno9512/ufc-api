/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.ufc.security;

import io.jsonwebtoken.Claims;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bruni
 */
class JwtServiceTest {
    
    private final JwtService jwtService = new JwtService();
    
    @Test
    void deveGerarToken(){
        
        String token = jwtService
                .gerarToken("admin", "ROLE_ADMIN");
        
        assertNotNull(token);
        
        assertFalse(token.isBlank());
        
    }
    
    @Test
    void deveExtrairUsername(){
        
        String token = jwtService
                .gerarToken("admin", "ROLE_ADMIN");
        
        String username = jwtService.extrairUsername(token);
        
        assertEquals("admin", username);
    }
    
    @Test
    void deveExtrairRole(){
        
        String token = jwtService
                .gerarToken("admin", "ROLE_ADMIN");
        
        String role = jwtService.extrairRole(token);
        
        assertEquals("ROLE_ADMIN", role);
    }
    
    @Test
    void deveExtrairClaims(){
        
        String token = jwtService
                .gerarToken("admin", "ROLE_ADMIN");
        
        Claims claims = jwtService.extrairClaims(token);
        
        assertEquals("admin", claims.getSubject());
        
        assertEquals("ROLE_ADMIN",
                claims.get("role"));
        
    }
    
    @Test
    void deveValidarToken(){
        
        String token = jwtService
                .gerarToken("admin", "ROLE_ADMIN");
        
        assertTrue(jwtService.validarToken(token));
    }
    
    @Test
    void deveRetornarFalseQuandoTokenForInvalido(){
        
        String token = "token.invalido.qualquer";
        
        assertFalse(jwtService.validarToken(token));
    }
    
    @Test
    void tokenNovoNaoDeveEstarExpirando(){
        
        String token = jwtService
                .gerarToken("admin", "ROLE_ADMIN");
        
        assertFalse(jwtService.tokenExpirado(token));
    }
    
}
