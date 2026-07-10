/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.ufc.repository;

import com.projeto.ufc.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author bruni
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);
    
}
