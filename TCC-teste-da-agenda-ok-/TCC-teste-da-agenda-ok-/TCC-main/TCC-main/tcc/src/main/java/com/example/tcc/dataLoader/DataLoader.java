package com.example.tcc.dataLoader;

import com.example.tcc.cadastro_model.CadastroModel;
import com.example.tcc.cadastro_repository.CadastroRepository;
import com.example.tcc.cadastro_model.OficinaModel;
import com.example.tcc.cadastro_repository.OficinaRepository;
import com.example.tcc.cadastro_service.NominatimService;
import com.example.tcc.cadastro_service.NominatimService; // <--- NOVO IMPORT
import com.example.tcc.cadastro_model.Coordenadas; // <--- NOVO IMPORT

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CadastroRepository cadastroRepository;
    private final OficinaRepository oficinaRepository;
    private final NominatimService nominatimService; // <--- INJEÇÃO DO SERVIÇO

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

            // 2. INSERIR OFICINA 1 (oficinaId = 1) - Localização Central
            inserirOficina("Rua Exemplo, 100, Salvador, BA", "Mecânica Central");

            // 3. INSERIR OFICINA 2 (oficinaId = 2) - Localização Distante (para teste de raio)
            inserirOficina("Avenida Litoranea, 500, São Luís, MA", "Auto Service Distante");

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
            oficina.setCnpj("000000000001" + oficinaRepository.count()); // CNPJ de teste
            oficina.setLatitude(coords.getLatitude()); // <--- CHAVE
            oficina.setLongitude(coords.getLongitude()); // <--- CHAVE

            // 3. SALVA NO MYSQL
            oficinaRepository.save(oficina);

        } catch (Exception e) {
            System.err.println("Aviso: Falha ao geocodificar ou inserir a oficina: " + nome + ". Erro: " + e.getMessage());
            // Se falhar, a aplicação continua, mas sem Lat/Lon válidas para esta oficina.
        }
    }
}