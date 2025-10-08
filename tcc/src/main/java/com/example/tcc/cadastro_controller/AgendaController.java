package com.example.tcc.cadastro_controller;

import com.example.tcc.cadastro_model.AgendaModel;
import com.example.tcc.cadastro_service.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    // [GET]: Para exibir a tela de calendário (status dos dias)
    // URL no Insomnia: http://localhost:8080/api/agenda/status?mes=10&ano=2025
    @GetMapping("/status")
    public ResponseEntity<Map<Integer, String>> obterStatusMes(
            @RequestParam int mes,
            @RequestParam int ano,
            @RequestParam Long oficinaId) {

        // Retorna um mapa onde a chave é o dia do mês (ex: 1, 2, 3...) e o valor é o Status (DISPONIVEL, INDISPONIVEL, EM_PROCESSO)
        Map<Integer, String> statusDoMes = agendaService.obterStatusDoMes(mes, ano, oficinaId);
        return ResponseEntity.ok(statusDoMes);
    }

    // [POST]: Para confirmar o agendamento (Botão CONFIRMAR)
    // URL no Insomnia: http://localhost:8080/api/agenda
    @PostMapping
    public ResponseEntity<AgendaModel> confirmarAgendamento(@RequestBody AgendaModel agendamento) {

        // Simples verificação de lógica de negócio no Controller
        if (agendamento.getDataAgendamento() == null) {
            return ResponseEntity.badRequest().build();
        }

        AgendaModel novoAgendamento = agendaService.criarAgendamento(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    // [GET]: Para listar agendamentos do usuário (Botão AGENDAMENTOS)
    // URL no Insomnia: http://localhost:8080/api/agenda/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public List<AgendaModel> listarAgendamentosUsuario(@PathVariable Long usuarioId) {
        return agendaService.listarAgendamentosPorUsuario(usuarioId);
    }
}
