package com.example.tcc.cadastro_repository;

import com.example.tcc.cadastro_model.OrcamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrcamentoRepository extends JpaRepository<OrcamentoModel, Long> {

    /**
     * Busca todos os orçamentos associados a um determinado ID de cliente
     * para montar o histórico.
     * O Spring Data JPA traduz automaticamente este nome de método para a consulta SQL correta.
     */
    List<OrcamentoModel> findByClienteId(Long clienteId);
}