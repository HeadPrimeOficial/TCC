package com.example.tcc.cadastro_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal; // Import para valores monetários

// Usaremos a abordagem manual que funcionou para as Oficinas, sem Lombok
@Entity
@Table(name = "servicos")
public class ServicoModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Ex: Troca de Óleo, Balanceamento
    private String descricao;

    // Usamos BigDecimal para garantir precisão monetária (preço base)
    private BigDecimal precoBase;

    // Pode ser usado para filtrar "Principais Serviços"
    private boolean emDestaque = false;

    // --- Mapeamento para a Oficina ---
    // Um Serviço está associado a uma Oficina específica (Muitos para Um)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id", nullable = false)
    private OficinaModel oficina;



    // CONSTRUTORES
    public ServicoModel() {}

    // GETTERS E SETTERS MANUAIS (para evitar conflitos com Lombok)

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }

    public boolean isEmDestaque() {
        return emDestaque;
    }

    public void setEmDestaque(boolean emDestaque) {
        this.emDestaque = emDestaque;
    }

    public OficinaModel getOficina() {
        return oficina;
    }

    // O GETTER QUE CAUSA O ERRO DE PROXY/LAZY LOADING
    @JsonIgnore // <--- ADICIONE ESTA ANOTAÇÃO


    public void setOficina(OficinaModel oficina) {
        this.oficina = oficina;

    }

}
