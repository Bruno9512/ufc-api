package com.projeto.ufc.security;

import com.projeto.ufc.model.Usuario;
import com.projeto.ufc.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository repository;

    public CustomUserDetailsService(
            UsuarioRepository repository) {

        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        Usuario usuario = repository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado"));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getRole())
                .build();
    }
}
