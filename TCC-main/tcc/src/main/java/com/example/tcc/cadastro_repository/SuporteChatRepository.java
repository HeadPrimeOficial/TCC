package com.example.tcc.cadastro_repository;


import com.example.tcc.cadastro_model.SuporteChatModel;
import com.example.tcc.cadastro_model.SuporteChatModel.Status; // Para usar o ENUM Status
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SuporteChatRepository extends JpaRepository<SuporteChatModel, Long> {

    /**
     * Busca um chat de suporte de um cliente que NÃO esteja no status FINALIZADO.
     * Isso permite que o cliente retome um chat que está PENDENTE ou EM_ATENDIMENTO.
     * * O Spring Data JPA traduz automaticamente:
     * findBy -> Busca por
     * ClienteId -> Campo 'cliente' com o ID
     * AndStatusNot -> E o Status não seja igual ao valor passado
     */
    Optional<SuporteChatModel> findByClienteIdAndStatusNot(Long clienteId, Status status);
}