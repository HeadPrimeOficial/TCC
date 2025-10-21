package com.example.tcc.cadastro_repository;


import com.example.tcc.cadastro_model.ItemOrcamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamentoModel, Long> {

    // Adicione métodos customizados se precisar buscar itens por orcamentoId, por exemplo:
    // List<ItemOrcamentoModel> findByOrcamentoId(Long orcamentoId);
}