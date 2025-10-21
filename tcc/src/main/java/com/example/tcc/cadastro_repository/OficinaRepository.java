package com.example.tcc.cadastro_repository;


import com.example.tcc.cadastro_model.OficinaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OficinaRepository extends JpaRepository<OficinaModel, Long> {
    // Não precisa de métodos aqui, o JpaRepository já fornece o save, findById, etc.
}