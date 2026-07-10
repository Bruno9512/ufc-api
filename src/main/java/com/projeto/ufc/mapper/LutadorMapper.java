/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.ufc.mapper;

import com.projeto.ufc.dto.LutadorDTO;
import com.projeto.ufc.model.Lutador;

/**
 *
 * @author bruni
 */
public final class LutadorMapper {
    
    private LutadorMapper(){
    
    }
    
    public static LutadorDTO toDTO(Lutador lutador){
        
        if(lutador == null){
            return null;
        }
        
        LutadorDTO dto = new LutadorDTO();
        
        dto.setId(lutador.getId());
        dto.setNome(lutador.getNome());
        dto.setPais(lutador.getPais());
        dto.setCategoria(lutador.getCategoria());
        dto.setAltura(lutador.getAltura());
        
        return dto;
    }
    
    public static Lutador toEntity(LutadorDTO dto){
        
        if(dto == null){
            return null;
        }
        
        Lutador lutador = new Lutador();
        
        lutador.setId(dto.getId());
        lutador.setNome(dto.getNome());
        lutador.setPais(dto.getPais());
        lutador.setCategoria(dto.getCategoria());
        lutador.setAltura(dto.getAltura());
        
        return lutador;
    }
}