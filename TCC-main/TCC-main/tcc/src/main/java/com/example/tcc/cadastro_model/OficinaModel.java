package com.example.tcc.cadastro_model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor; // Opcional, se precisar de um construtor com todos os campos

@Entity
@Data

@Table(name = "oficinas")
public class OficinaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;

    public OficinaModel() {
        this.id = id;
    }

    public OficinaModel(Long id, String nome, String cnpj) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // Remova qualquer construtor manual que você tenha criado para manter o código limpo
}