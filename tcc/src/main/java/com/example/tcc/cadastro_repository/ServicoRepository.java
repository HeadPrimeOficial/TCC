package com.example.tcc.cadastro_repository;

import com.example.tcc.cadastro_model.ServicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicoRepository extends JpaRepository<ServicoModel, Long> {

    // 1. Busca por nome (usada na barra de busca)
    List<ServicoModel> findByNomeContainingIgnoreCase(String nome);

    // 2. Busca por serviços em destaque (usada para "Principais Serviços")
    List<ServicoModel> findByEmDestaqueTrue();

    // 3. Busca serviços de uma oficina específica por nome
    List<ServicoModel> findByOficinaIdAndNomeContainingIgnoreCase(Long oficinaId, String nome);
}