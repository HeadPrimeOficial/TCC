package com.example.tcc.cadastro_repository;



import com.example.tcc.cadastro_model.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroRepository extends JpaRepository<CadastroModel, Long> {
}
