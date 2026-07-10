package com.projeto.ufc.service;

import com.projeto.ufc.model.Usuario;
import com.projeto.ufc.repository.UsuarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(
            UsuarioRepository repository,
            BCryptPasswordEncoder encoder) {

        this.repository = repository;
        this.encoder = encoder;
    }

    public Usuario salvar(Usuario usuario) {

        usuario.setPassword(
                encoder.encode(
                        usuario.getPassword()));

        return repository.save(usuario);
    }
}