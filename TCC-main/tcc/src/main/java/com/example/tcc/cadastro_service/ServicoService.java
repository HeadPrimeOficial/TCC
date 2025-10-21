package com.example.tcc.cadastro_service;

import com.example.tcc.cadastro_model.ServicoModel;
import com.example.tcc.cadastro_repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<ServicoModel> buscarPorNome(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return servicoRepository.findAll();
        }
        return servicoRepository.findByNomeContainingIgnoreCase(termo);
    }

    public List<ServicoModel> buscarDestaques() {
        return servicoRepository.findByEmDestaqueTrue();
    }

    // Lógica para buscar serviços em uma oficina específica
    public List<ServicoModel> buscarPorOficinaENome(Long oficinaId, String termo) {
        return servicoRepository.findByOficinaIdAndNomeContainingIgnoreCase(oficinaId, termo);
    }
}