package com.example.tcc.dataLoader;


import com.example.tcc.cadastro_model.CadastroModel;
import com.example.tcc.cadastro_repository.CadastroRepository;
import com.example.tcc.cadastro_model.OficinaModel;
import com.example.tcc.cadastro_repository.OficinaRepository;
import com.example.tcc.cadastro_service.NominatimService;
import com.example.tcc.cadastro_model.Coordenadas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CadastroRepository cadastroRepository;
    private final OficinaRepository oficinaRepository;
    private final NominatimService nominatimService;

    public DataLoader(CadastroRepository cadastroRepository, OficinaRepository oficinaRepository, NominatimService nominatimService) {
        this.cadastroRepository = cadastroRepository;
        this.oficinaRepository = oficinaRepository;
        this.nominatimService = nominatimService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (cadastroRepository.count() == 0) {

            System.out.println("----------------------------------------");
            System.out.println("INSERINDO DADOS INICIAIS DE TESTE...");

            // 1. INSERIR USUÁRIO DE TESTE (usuarioId = 1)
            CadastroModel usuario = new CadastroModel();
            usuario.setNome("Usuario Mapa");
            usuario.setEmail("mapa@teste.com");
            usuario.setSenha("123");
            cadastroRepository.save(usuario);
// Endereço de Exemplo Real (Substitua por um de sua escolha!)
// 2. INSERIR OFICINA 1 (Localização Central)
            inserirOficina("Avenida Otávio Mangabeira, 500, Salvador, BA, Brasil", "Mecânica Central");

// 3. INSERIR OFICINA 2 (Localização Distante)
            inserirOficina("Praça da Sé, 21, São Paulo, SP, Brasil", "Auto Service Distante");

            System.out.println("Dados de teste e coordenadas inseridos com sucesso.");
            System.out.println("----------------------------------------");
        }
    }

    /**
     * Método auxiliar para geocodificar e salvar a oficina.
     */
    private void inserirOficina(String endereco, String nome) {
        try {
            // 1. OBTÉM COORDENADAS DO ENDEREÇO
            Coordenadas coords = nominatimService.obterCoordenadas(endereco);

            // 2. CRIA E PREENCHE O MODELO
            OficinaModel oficina = new OficinaModel();
            oficina.setNome(nome);
            oficina.setCnpj("000000000001" + oficinaRepository.count());

            // **A CORREÇÃO:** Injeta o objeto Coordenadas inteiro no campo @Embedded
            oficina.setCoordenadas(coords);

            // 3. SALVA NO MYSQL
            oficinaRepository.save(oficina);

        } catch (Exception e) {
            System.err.println("Aviso: Falha ao geocodificar ou inserir a oficina: " + nome + ". Erro: " + e.getMessage());
            // Se falhar, a aplicação continua sem a Lat/Lon para esta oficina.
        }
    }
}