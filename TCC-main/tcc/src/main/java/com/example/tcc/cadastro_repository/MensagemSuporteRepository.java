package com.example.tcc.cadastro_repository;


import com.example.tcc.cadastro_model.MensagemSuporteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensagemSuporteRepository extends JpaRepository<MensagemSuporteModel, Long> {

    /**
     * Busca todas as mensagens associadas a um ID de chat específico,
     * ordenando-as pela data de envio de forma ascendente (da mais antiga para a mais nova).
     * * O Spring Data JPA traduz automaticamente:
     * findBy -> Busca por
     * ChatId -> Campo 'chat' com o ID
     * OrderByDataEnvioAsc -> Ordena pela 'dataEnvio' de forma ascendente
     */
    List<MensagemSuporteModel> findByChatIdOrderByDataEnvioAsc(Long chatId);
}