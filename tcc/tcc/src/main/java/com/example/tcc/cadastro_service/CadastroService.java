package com.example.tcc.cadastro_service;


import com.example.tcc.cadastro_model.CadastroModel;
import com.example.tcc.cadastro_repository.CadastroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroService {

    private final CadastroRepository cadastroRepository;

    public CadastroService(CadastroRepository cadastroRepository) {
        this.cadastroRepository = cadastroRepository;
    }

    public List<CadastroModel> listarTodos() {
        return cadastroRepository.findAll();
    }

    public Optional<CadastroModel> buscarPorId(Long id) {
        return cadastroRepository.findById(id);
    }

    public CadastroModel salvar(CadastroModel cadastro) {
        return cadastroRepository.save(cadastro);
    }

    public void deletar(Long id) {
        cadastroRepository.deleteById(id);
    }
}
