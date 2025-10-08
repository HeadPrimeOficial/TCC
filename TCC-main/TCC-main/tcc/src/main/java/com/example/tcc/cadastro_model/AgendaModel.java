package com.example.tcc.cadastro_model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "agendamentos")
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A data e hora do agendamento (o dia selecionado)
    private LocalDate dataAgendamento;

    // Relacionamento com o usuário que está agendando
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private CadastroModel usuario;

    // Status do agendamento: PENDENTE, CONFIRMADO, CANCELADO, etc.
    private String status;

    // CORREÇÃO AQUI: ADICIONE ESTE CAMPO para o Repository encontrar 'oficinaId'
    private Long oficinaId;

    public AgendaModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public CadastroModel getUsuario() {
        return usuario;
    }

    public void setUsuario(CadastroModel usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public Long getOficinaId() {
        return oficinaId;
    }

    public void setOficinaId(Long oficinaId) {
        this.oficinaId = oficinaId;
    }

    // Se o Lombok não está gerando automaticamente, adicione o setter manualmente:
    public void setStatus(String status) {
        this.status = status;
    }
}