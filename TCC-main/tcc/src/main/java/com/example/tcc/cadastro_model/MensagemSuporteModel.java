package com.example.tcc.cadastro_model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens_suporte")
public class MensagemSuporteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A qual sessão de chat esta mensagem pertence
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private SuporteChatModel chat;

    private String conteudo;

    // Identifica quem enviou (true = cliente; false = atendente/suporte)
    private boolean enviadaPeloCliente;

    private LocalDateTime dataEnvio = LocalDateTime.now();

    public MensagemSuporteModel() {
        this.id = id;
    }

    public MensagemSuporteModel(Long id, SuporteChatModel chat, String conteudo, boolean enviadaPeloCliente, LocalDateTime dataEnvio) {
        this.id = id;
        this.chat = chat;
        this.conteudo = conteudo;
        this.enviadaPeloCliente = enviadaPeloCliente;
        this.dataEnvio = dataEnvio;
    }


    // Lembre-se de aplicar o @JsonIgnore no getter de entidades Lazy
    @JsonIgnore
    public SuporteChatModel getChat() {
        return chat;
    }
    // ... (Defina todos os outros getters e setters)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChat(SuporteChatModel chat) {
        this.chat = chat;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public boolean isEnviadaPeloCliente() {
        return enviadaPeloCliente;
    }

    public void setEnviadaPeloCliente(boolean enviadaPeloCliente) {
        this.enviadaPeloCliente = enviadaPeloCliente;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}