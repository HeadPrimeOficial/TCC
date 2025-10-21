package com.example.tcc.cadastro_controller;


import com.example.tcc.cadastro_model.SuporteChatModel;
import com.example.tcc.cadastro_model.MensagemSuporteModel;
import com.example.tcc.cadastro_service.SuporteChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/suporte")
public class SuporteChatController {

    private final SuporteChatService chatService;

    public SuporteChatController(SuporteChatService chatService) {
        this.chatService = chatService;
    }

    // 1. Inicia um novo chat ou retorna o chat ABERTO existente (Ação "NOVO CHAT")
    // Ex: GET /api/chat/suporte/iniciar/1 (ID do Cliente)
    @GetMapping("/iniciar/{clienteId}")
    public SuporteChatModel iniciarChat(@PathVariable Long clienteId) {
        return chatService.iniciarOuContinuarChat(clienteId);
    }

    // 2. Busca o histórico de mensagens da sessão
    // Ex: GET /api/chat/suporte/mensagens/5 (ID do Chat)
    @GetMapping("/mensagens/{chatId}")
    public List<MensagemSuporteModel> getMensagens(@PathVariable Long chatId) {
        return chatService.buscarMensagens(chatId);
    }

    // 3. Envia uma nova mensagem
    // Ex: POST /api/chat/suporte/enviar/5 (ID do Chat)
    @PostMapping("/enviar/{chatId}")
    public MensagemSuporteModel enviarMensagem(
            @PathVariable Long chatId,
            @RequestParam String conteudo) {

        // Aqui, assumimos que quem está enviando é sempre o cliente para este endpoint
        // O atendente teria um endpoint diferente no seu sistema.
        return chatService.enviarMensagem(chatId, conteudo, true); // true = enviada pelo cliente
    }

    // 4. Finaliza o chat (Ação "FINALIZAR")
    // Ex: PUT /api/chat/suporte/finalizar/5 (ID do Chat)
    @PutMapping("/finalizar/{chatId}")
    public SuporteChatModel finalizarChat(@PathVariable Long chatId) {
        return chatService.finalizarChat(chatId);
    }
}