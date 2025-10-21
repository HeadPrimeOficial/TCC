package com.example.tcc.cadastro_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "suporte_chats")
public class SuporteChatModel {

    public enum Status {
        ABERTO, EM_ATENDIMENTO, FINALIZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente (usuário) que abriu o chat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private CadastroModel cliente;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ABERTO;

    private LocalDateTime dataAbertura = LocalDateTime.now();
    private LocalDateTime dataFechamento;

    // Campo para identificar qual atendente está cuidando do chat (pode ser null)
    private Long atendenteId;

    public SuporteChatModel(Long id) {
        this.id = id;
    }

    public SuporteChatModel(Long id, CadastroModel cliente, Status status, LocalDateTime dataAbertura, LocalDateTime dataFechamento, Long atendenteId) {
        this.id = id;
        this.cliente = cliente;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.atendenteId = atendenteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCliente(CadastroModel cliente) {
        this.cliente = cliente;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Long getAtendenteId() {
        return atendenteId;
    }

    public void setAtendenteId(Long atendenteId) {
        this.atendenteId = atendenteId;
    }

    public SuporteChatModel() {}

    // Lembre-se de aplicar o @JsonIgnore no getter de entidades Lazy
    @JsonIgnore
    public CadastroModel getCliente() {
        return cliente;
    }
    // ... (Defina todos os outros getters e setters)
}