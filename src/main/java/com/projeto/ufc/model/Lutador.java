package com.projeto.ufc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "lutadores")
public class Lutador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "País é obrigatório")
    private String pais;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Altura é obrigatória")
    @Positive(message = "Altura deve ser maior que zero")
    private Double altura;

    public Lutador() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "Lutador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pais='" + pais + '\'' +
                ", categoria='" + categoria + '\'' +
                ", altura=" + altura +
                '}';
    }
}