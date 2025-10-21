package com.example.tcc.cadastro_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_orcamento")
public class ItemOrcamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Item pertence a um Orçamento (Muitos para Um)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private OrcamentoModel orcamento;

    private String nome; // Nome do Serviço/Peça: "Troca de pastilhas", "Filtro de Óleo"
    private BigDecimal precoUnitario;
    private Integer quantidade;

    public ItemOrcamentoModel(Long id) {
        this.id = id;
    }

    public ItemOrcamentoModel(Long id, OrcamentoModel orcamento, String nome, BigDecimal precoUnitario, Integer quantidade) {
        this.id = id;
        this.orcamento = orcamento;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrcamentoModel getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(OrcamentoModel orcamento) {
        this.orcamento = orcamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    // O GETTER QUE CAUSA O ERRO DE PROXY/LAZY LOADING
    @JsonIgnore // <--- ADICIONE ESTA ANOTAÇÃO

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    // GETTERS/SETTERS (Manuais) e Construtor Vazio
    // ... (Defina todos os getters e setters)
}