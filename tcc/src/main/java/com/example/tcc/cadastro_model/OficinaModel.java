package com.example.tcc.cadastro_model;

import jakarta.persistence.*;

@Entity
@Table(name = "oficinas")
public class OficinaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;

    @Embedded
    private Coordenadas coordenadas;

    // 1. CONSTRUTOR VAZIO (Obrigatório para JPA e para 'new OficinaModel()')
    public OficinaModel() {}

    // 2. GETTERS E SETTERS PARA O CAMPO 'coordenadas' (Cruciais para o Mapa)
    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    // 3. GETTERS E SETTERS PARA OS DEMAIS CAMPOS (Cruciais para o DataLoader)

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}