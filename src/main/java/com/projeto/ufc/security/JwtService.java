package com.projeto.ufc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger logger =
            LoggerFactory.getLogger(JwtService.class);

    private static final String SECRET =
            "minha-chave-secreta-ufc-api-2026-seguranca";

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

 
    public String gerarToken(String username, String role) {

        logger.info("Gerando token JWT para o usuário {}", username);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 86400000)
            )
                .signWith(key)
                .compact();
        
    }


   
    public Claims extrairClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    
    public String extrairUsername(String token) {

        return extrairClaims(token)
                .getSubject();
    }

    
    public String extrairRole(String token) {

        return extrairClaims(token)
                .get("role", String.class);
    }

    
    public boolean tokenExpirado(String token) {

        return extrairClaims(token)
                .getExpiration()
                .before(new Date());
    }

    
    public boolean validarToken(String token) {

        try {

            return !tokenExpirado(token);

        } catch (Exception e) {

            logger.warn("Falha ao validar token JWT: {}", e.getMessage());

            return false;
        }
    }
}