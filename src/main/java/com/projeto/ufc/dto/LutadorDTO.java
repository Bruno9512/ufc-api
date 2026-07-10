package com.projeto.ufc.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 *
 * @author bruni
 */
@Schema(description = "Respresenta um lutador do UFC")
public class LutadorDTO {
    
    @Schema(description = "Identificador do lutador" , example = "1")
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do lutador" , example = "Anderson Silva")
    private String nome;
    
    @NotBlank(message = "País é obrigatório")
    @Schema(description = "Pais de origem" , example = "Brasil")
    private String pais;
    
    @NotBlank(message = "Categoria é obrigatória")
    @Schema(description = "Categoria do lutador" , example = "Peso Médio")
    private String categoria;
    
    @NotNull(message = "Altura é obrigatória")
    @Positive(message = "Altura deve ser maior que zero")
    @Schema(description = "Altura em metros" , example = "1.88")
    private Double altura;
    
    public LutadorDTO(){
        }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getPais(){
        return pais;
    }
    
    public void setPais(String pais){
        this.pais = pais;
    }
    
    public String getCategoria(){
        return categoria;
    }
    
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    
    public Double getAltura(){
        return altura;
    }
    
    public void setAltura(Double altura){
        this.altura = altura;
    }
    
    
}

    