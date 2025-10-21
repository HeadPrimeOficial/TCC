package com.example.tcc.cadastro_controller;


import com.example.tcc.cadastro_model.OrcamentoModel;
import com.example.tcc.cadastro_service.OrcamentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    // 1. Endpoint para buscar o histórico de orçamentos de um cliente
    // Ex: GET /api/orcamentos/historico/1 (ID do Cliente)
    @GetMapping("/historico/{clienteId}")
    public List<OrcamentoModel> getHistoricoOrcamentos(@PathVariable Long clienteId) {
        return orcamentoService.buscarHistorico(clienteId);
    }

    // 2. Endpoint para o cliente ACEITAR um orçamento
    // Ex: PUT /api/orcamentos/aceitar/10 (ID do Orçamento)
    @PutMapping("/aceitar/{orcamentoId}")
    public OrcamentoModel aceitarOrcamento(@PathVariable Long orcamentoId) {
        return orcamentoService.aceitarOrcamento(orcamentoId);
    }

    // 3. Endpoint para o cliente FINALIZAR (ou Oficina, dependendo do fluxo)
    // Ex: PUT /api/orcamentos/finalizar/10
    @PutMapping("/finalizar/{orcamentoId}")
    public OrcamentoModel finalizarOrcamento(@PathVariable Long orcamentoId) {
        return orcamentoService.finalizarOrcamento(orcamentoId);
    }

    // ... (Outros endpoints como recusar, ou POST para criar um novo)
}