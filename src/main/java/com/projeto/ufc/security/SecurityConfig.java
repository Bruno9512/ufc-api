package com.projeto.ufc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            // API REST não utiliza sessão
            .csrf(csrf -> csrf.disable())

            // Sessões desabilitadas (JWT é stateless)
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS))

            // Configuração das permissões
            .authorizeHttpRequests(auth -> auth

                    // Endpoints públicos
                    .requestMatchers(
                            "/auth/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**"
                    ).permitAll()

                    // Qualquer outra rota exige autenticação
                    .anyRequest().authenticated()
            )

            // Executa o filtro JWT antes do filtro padrão do Spring
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}