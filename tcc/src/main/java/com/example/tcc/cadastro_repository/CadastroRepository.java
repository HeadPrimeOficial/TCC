package com.example.tcc.cadastro_repository;



import com.example.tcc.cadastro_model.CadastroModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadastroRepository extends JpaRepository<CadastroModel, Long> {
    Optional<CadastroModel> findByEmail(String email);
}
