package com.example.tcc.cadastro_service;

import com.example.tcc.cadastro_model.SuporteChatModel;
import com.example.tcc.cadastro_model.MensagemSuporteModel;
import com.example.tcc.cadastro_repository.SuporteChatRepository;
import com.example.tcc.cadastro_repository.MensagemSuporteRepository;
import com.example.tcc.cadastro_repository.CadastroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SuporteChatService {

    private final SuporteChatRepository chatRepository;
    private final MensagemSuporteRepository mensagemRepository;
    private final CadastroRepository cadastroRepository;

    public SuporteChatService(SuporteChatRepository chatRepository, MensagemSuporteRepository mensagemRepository, CadastroRepository cadastroRepository) {
        this.chatRepository = chatRepository;
        this.mensagemRepository = mensagemRepository;
        this.cadastroRepository = cadastroRepository;
    }

    /**
     * 1. Ação "NOVO CHAT" ou continuar o chat existente.
     * Tenta buscar um chat ABERTO do cliente. Se não houver, cria um novo.
     */
    @Transactional
    public SuporteChatModel iniciarOuContinuarChat(Long clienteId) {
        // Busca um chat não FINALIZADO para este cliente
        return chatRepository.findByClienteIdAndStatusNot(clienteId, SuporteChatModel.Status.FINALIZADO)
                .orElseGet(() -> {
                    // Se não encontrar, cria um novo
                    SuporteChatModel novoChat = new SuporteChatModel();
                    novoChat.setCliente(cadastroRepository.findById(clienteId)
                            .orElseThrow(() -> new RuntimeException("Cliente não encontrado.")));
                    novoChat.setStatus(SuporteChatModel.Status.ABERTO);

                    // Adiciona uma mensagem inicial do "Suporte" (Olá Tiago, em que posso ajudar?)
                    SuporteChatModel chatSalvo = chatRepository.save(novoChat);
                    enviarMensagemInicial(chatSalvo);

                    return chatSalvo;
                });
    }

    /**
     * 2. Enviar mensagem.
     */
    @Transactional
    public MensagemSuporteModel enviarMensagem(Long chatId, String conteudo, boolean isCliente) {
        SuporteChatModel chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat não encontrado."));

        if (chat.getStatus() == SuporteChatModel.Status.FINALIZADO) {
            throw new IllegalStateException("O chat está finalizado.");
        }

        MensagemSuporteModel mensagem = new MensagemSuporteModel();
        mensagem.setChat(chat);
        mensagem.setConteudo(conteudo);
        mensagem.setEnviadaPeloCliente(isCliente);

        // Se for a primeira mensagem do cliente e o status for ABERTO, muda para EM_ATENDIMENTO
        if (isCliente && chat.getStatus() == SuporteChatModel.Status.ABERTO) {
            chat.setStatus(SuporteChatModel.Status.EM_ATENDIMENTO);
            chatRepository.save(chat);
        }

        return mensagemRepository.save(mensagem);
    }

    /**
     * 3. Ação "FINALIZAR".
     */
    @Transactional
    public SuporteChatModel finalizarChat(Long chatId) {
        SuporteChatModel chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat não encontrado."));

        chat.setStatus(SuporteChatModel.Status.FINALIZADO);
        chat.setDataFechamento(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    /**
     * Busca o histórico de mensagens para um chat específico.
     */
    public List<MensagemSuporteModel> buscarMensagens(Long chatId) {
        return mensagemRepository.findByChatIdOrderByDataEnvioAsc(chatId);
    }

    private void enviarMensagemInicial(SuporteChatModel chat) {
        MensagemSuporteModel saudacao = new MensagemSuporteModel();
        saudacao.setChat(chat);
        saudacao.setConteudo("Olá, em que posso ajudar?");
        saudacao.setEnviadaPeloCliente(false); // Enviada pelo Suporte
        mensagemRepository.save(saudacao);
    }
}