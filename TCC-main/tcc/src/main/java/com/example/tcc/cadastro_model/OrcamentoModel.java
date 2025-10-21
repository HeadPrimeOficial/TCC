package com.example.tcc.cadastro_model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orcamentos")
public class OrcamentoModel {

    // Status do Orçamento
    public enum Status {
        PENDENTE, ACEITO, RECUSADO, FINALIZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Relações com outras entidades ---


    // Cliente que solicitou o orçamento (Usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private CadastroModel cliente;



    // Oficina que emitiu o orçamento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id", nullable = false)
    private OficinaModel oficina;

    // --- Detalhes do Orçamento ---

    // O valor total que aparece em 'Valor: R$ 280,00'
    private BigDecimal valorTotal;

    // 'Disponibilidade: Hoje, às 14h'
    private LocalDateTime dataDisponibilidade;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDENTE; // Começa como PENDENTE

    // Data de criação
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // --- Itens do Orçamento (Lista de Serviços/Peças) ---
    // Usaremos OneToMany para listar os itens (implementado abaixo)

    // CONSTRUTOR VAZIO E GETTERS/SETTERS (Manuais, como antes)

    public OrcamentoModel() {}

    public OrcamentoModel(Long id) {
        this.id = id;
    }

    public OrcamentoModel(Long id, CadastroModel cliente, OficinaModel oficina, BigDecimal valorTotal, LocalDateTime dataDisponibilidade, Status status, LocalDateTime dataCriacao) {
        this.id = id;
        this.cliente = cliente;
        this.oficina = oficina;
        this.valorTotal = valorTotal;
        this.dataDisponibilidade = dataDisponibilidade;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @JsonIgnore
    public CadastroModel getCliente() {
        return cliente;
    }

    public void setCliente(CadastroModel cliente) {
        this.cliente = cliente;
    }

    @JsonIgnore
    public OficinaModel getOficina() {
        return oficina;

    }




    public void setOficina(OficinaModel oficina) {
        this.oficina = oficina;
    }




    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataDisponibilidade() {
        return dataDisponibilidade;
    }

    public void setDataDisponibilidade(LocalDateTime dataDisponibilidade) {
        this.dataDisponibilidade = dataDisponibilidade;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }



    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    // GETTERS E SETTERS MANUAIS
    // ... (Defina todos os getters e setters para evitar o erro 404/Hibernate)
    // Lembre-se de usar @JsonIgnore nos getters de objetos Lazy como 'cliente' e 'oficina'

    // Exemplo do getter com JsonIgnore para evitar o erro de proxy do Jackson:
    // @JsonIgnore
    // public CadastroModel getCliente() { return cliente; }
    // public void setCliente(CadastroModel cliente) { this.cliente = cliente; }

    // ... (Defina o restante dos métodos)
}