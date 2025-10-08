package com.example.tcc.dataLoader;

import com.example.tcc.cadastro_model.CadastroModel;
import com.example.tcc.cadastro_repository.CadastroRepository;
import com.example.tcc.cadastro_model.OficinaModel;
import com.example.tcc.cadastro_repository.OficinaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CadastroRepository cadastroRepository;
    private final OficinaRepository oficinaRepository;

    // O Spring injeta automaticamente os repositórios aqui
    public DataLoader(CadastroRepository cadastroRepository, OficinaRepository oficinaRepository) {
        this.cadastroRepository = cadastroRepository;
        this.oficinaRepository = oficinaRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Esta verificação garante que os dados sejam inseridos apenas na primeira execução
        // (quando o banco está vazio). No MySQL, é uma boa prática para evitar duplicidade.
        if (cadastroRepository.count() == 0) {

            System.out.println("----------------------------------------");
            System.out.println("INSERINDO DADOS INICIAIS DE TESTE...");

            // 1. INSERIR USUÁRIO DE TESTE (usuarioId)
            // Este usuário será usado para fazer login e agendar
            CadastroModel usuario = new CadastroModel();
            usuario.setNome("Usuario Agenda");
            usuario.setEmail("agenda@teste.com");
            usuario.setSenha("12345");
            CadastroModel usuarioSalvo = cadastroRepository.save(usuario);

            // 2. INSERIR OFICINA DE TESTE (oficinaId)
            // Esta oficina terá a agenda testada
            OficinaModel oficina = new OficinaModel();
            oficina.setNome("Mecânica Fictícia");
            oficina.setCnpj("99999999000199");
            OficinaModel oficinaSalva = oficinaRepository.save(oficina);

            System.out.println("Dados inseridos com sucesso!");
            System.out.println("--> usuarioId para testes: " + usuarioSalvo.getId());
            System.out.println("--> oficinaId para testes: " + oficinaSalva.getId());
            System.out.println("----------------------------------------");
        }
    }
}