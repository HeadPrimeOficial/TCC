package com.example.tcc.cadastro_controller;


import com.example.tcc.cadastro_model.ServicoModel;
import com.example.tcc.cadastro_service.ServicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    // Endpoint para a barra de busca (Digita o Serviço)
    // Ex: GET /api/servicos/buscar?termo=revisao
    @GetMapping("/buscar")
    public List<ServicoModel> buscarServicos(
            @RequestParam(required = false) String termo) {
        return servicoService.buscarPorNome(termo);
    }

    // Endpoint para os "Principais Serviços" em destaque
    // Ex: GET /api/servicos/destaques
    @GetMapping("/destaques")
    public List<ServicoModel> buscarDestaques() {
        return servicoService.buscarDestaques();
    }

    // IMPORTANTE: Para uma oficina específica (se o cliente já selecionou uma)
    // Ex: GET /api/servicos/oficina/1?termo=oleo
    @GetMapping("/oficina/{oficinaId}")
    public List<ServicoModel> buscarPorOficina(
            @PathVariable Long oficinaId,
            @RequestParam(required = false) String termo) {
        return servicoService.buscarPorOficinaENome(oficinaId, termo);
    }
}