package com.projeto.ufc.security;

import jakarta.servlet.FilterChain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        filter = new JwtAuthenticationFilter(jwtService);

        SecurityContextHolder.clearContext();
    }

    @Test
    void devePermitirRequisicaoSemAuthorization() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn(null);

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
    }

    @Test
    void devePermitirHeaderInvalido() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Token abc123");

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
    }

    @Test
    void deveIgnorarTokenInvalido() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token");

        when(jwtService.validarToken("token"))
                .thenReturn(false);

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
    }

    @Test
    void deveAutenticarUsuarioComTokenValido() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token");

        when(jwtService.validarToken("token"))
                .thenReturn(true);

        when(jwtService.extrairUsername("token"))
                .thenReturn("admin");

        when(jwtService.extrairRole("token"))
                .thenReturn("ROLE_ADMIN");

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);

        assertNotNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );

        assertEquals(
                "admin",
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        );

        assertTrue(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority().equals("ROLE_ADMIN"))
        );
    }

    @Test
    void deveContinuarFiltroMesmoComTokenInvalido() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token");

        when(jwtService.validarToken("token"))
                .thenReturn(false);

        filter.doFilter(request, response, filterChain);

        verify(filterChain, times(1))
                .doFilter(request, response);
    }
}