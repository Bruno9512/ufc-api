package com.projeto.ufc.repository;

import com.projeto.ufc.model.Lutador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LutadorRepository extends JpaRepository<Lutador, Long> {
    
    List<Lutador> findByNomeContainingIgnoreCase(String nome);
    
    List<Lutador> findByPaisIgnoreCase(String pais);
    
    @Query("""
           SELECT l
           FROM Lutador l
           WHERE l.altura > :altura
           """)
    
    List<Lutador> buscarMaisAltosQue(
            @Param("altura")Double altura);
    
    List<Lutador> findByCategoriaIgnoreCase(String categoria);

}