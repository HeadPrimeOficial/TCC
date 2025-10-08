package com.example.tcc.cadastro_service;

import com.example.tcc.cadastro_model.AgendaModel;
import com.example.tcc.cadastro_repository.AgendaRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;
    // Defina a capacidade máxima de agendamentos por dia da oficina (exemplo)
    private static final int CAPACIDADE_MAXIMA_DIARIA = 5;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Map<Integer, String> obterStatusDoMes(int mes, int ano, Long oficinaId) {
        Map<Integer, String> statusPorDia = new HashMap<>();
        LocalDate inicioDoMes = LocalDate.of(ano, mes, 1);
        int diasNoMes = inicioDoMes.lengthOfMonth();

        for (int dia = 1; dia <= diasNoMes; dia++) {
            LocalDate dataAtual = LocalDate.of(ano, mes, dia);

            // 1. Regras de Negócio (ex: Finais de semana estão indisponíveis)
            if (dataAtual.getDayOfWeek() == DayOfWeek.SATURDAY || dataAtual.getDayOfWeek() == DayOfWeek.SUNDAY) {
                statusPorDia.put(dia, "INDISPONIVEL");
                continue;
            }

            // 2. Consulta de Agendamentos (assumindo que o repositório tem um método de contagem)
            long agendamentosDoDia = agendaRepository.countByDataAgendamentoAndOficinaId(dataAtual, oficinaId);

            if (agendamentosDoDia >= CAPACIDADE_MAXIMA_DIARIA) {
                statusPorDia.put(dia, "INDISPONIVEL");
            } else if (agendamentosDoDia > CAPACIDADE_MAXIMA_DIARIA * 0.5) {
                statusPorDia.put(dia, "EM_PROCESSO"); // Se 50% ou mais estiver agendado
            } else {
                statusPorDia.put(dia, "DISPONIVEL");
            }
        }

        return statusPorDia;
    }

    public AgendaModel criarAgendamento(AgendaModel agendamento) {
        // Validações adicionais (se a data ainda está disponível, se o usuário existe, etc.)
        // ...
        agendamento.setStatus("PENDENTE");
        return agendaRepository.save(agendamento);
    }

    public List<AgendaModel> listarAgendamentosPorUsuario(Long usuarioId) {
        // Assume que você tem um método no repositório: findByUsuarioId
        return agendaRepository.findByUsuarioId(usuarioId);
    }
}
