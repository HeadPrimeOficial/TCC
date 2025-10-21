package com.example.tcc.cadastro_repository;

import com.example.tcc.cadastro_model.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {

    /**
     * Usado pelo AgendaService para calcular a disponibilidade de um dia.
     * Conta quantos agendamentos existem para uma data específica e uma oficina.
     * (Ex: Quantos agendamentos a Oficina 'X' tem no dia 15/10/2025)
     */
    long countByDataAgendamentoAndOficinaId(LocalDate dataAgendamento, Long oficinaId);

    /**
     * Usado para listar todos os agendamentos de um usuário específico.
     * (Botão 'AGENDAMENTOS' do protótipo)
     */
    List<AgendaModel> findByUsuarioId(Long usuarioId);
}