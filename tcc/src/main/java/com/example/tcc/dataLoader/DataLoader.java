package com.example.tcc.dataLoader;


import com.example.tcc.cadastro_model.CadastroModel;
import com.example.tcc.cadastro_model.ServicoModel;
import com.example.tcc.cadastro_repository.CadastroRepository;
import com.example.tcc.cadastro_model.OficinaModel;
import com.example.tcc.cadastro_repository.OficinaRepository;
import com.example.tcc.cadastro_repository.ServicoRepository;
import com.example.tcc.cadastro_service.NominatimService;
import com.example.tcc.cadastro_model.Coordenadas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal; // <--- NOVO IMPORT NECESSÁRIO!

@Component
public class DataLoader implements CommandLineRunner {

    private final CadastroRepository cadastroRepository;
    private final OficinaRepository oficinaRepository;
    private final NominatimService nominatimService;
    private final ServicoRepository servicoRepository;

    public DataLoader(CadastroRepository cadastroRepository, OficinaRepository oficinaRepository, NominatimService nominatimService, ServicoRepository servicoRepository) {
        this.cadastroRepository = cadastroRepository;
        this.oficinaRepository = oficinaRepository;
        this.nominatimService = nominatimService;
        this.servicoRepository = servicoRepository;
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

            // Variável para armazenar o ID da Oficina 1, assumindo que será o primeiro ID gerado (1L)
            final Long oficinaId1 = 1L;

            // 2. INSERIR OFICINA 1 (Localização Central)
            inserirOficina("Avenida Otávio Mangabeira, 500, Salvador, BA, Brasil", "Mecânica Central");

            // 3. INSERIR OFICINA 2 (Localização Distante)
            // Se esta for inserida, terá o ID 2L
            inserirOficina("Praça da Sé, 21, São Paulo, SP, Brasil", "Auto Service Distante");

            // --- INSERÇÃO DE SERVIÇOS DE TESTE ---
            System.out.println("INSERINDO SERVIÇOS DE TESTE...");

            // Serviços em Destaque (Motor, Revisão, Pneu) - Associa à Oficina com ID 1
            inserirServico("Motor", "Diagnóstico completo do motor.", new BigDecimal("250.00"), true, oficinaId1);
            inserirServico("Revisão", "Revisão básica completa.", new BigDecimal("150.00"), true, oficinaId1);
            inserirServico("Pneu", "Troca e balanceamento de um pneu.", new BigDecimal("50.00"), true, oficinaId1);

            // Outros Serviços (não em destaque)
            inserirServico("Alinhamento", "Alinhamento 3D de rodas.", new BigDecimal("80.00"), false, oficinaId1);
            inserirServico("Troca de Óleo", "Troca de óleo e filtro.", new BigDecimal("120.00"), false, oficinaId1);

            // Serviços para a Oficina 2 (Exemplo)
            inserirServico("Eletrica", "Reparo em chicote e alternador.", new BigDecimal("300.00"), false, 2L);


            System.out.println("Dados de teste e coordenadas inseridos com sucesso.");
            System.out.println("Serviços de teste inseridos com sucesso.");
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

            // Injeta o objeto Coordenadas inteiro no campo @Embedded
            oficina.setCoordenadas(coords);

            // 3. SALVA NO MYSQL
            oficinaRepository.save(oficina);

        } catch (Exception e) {
            System.err.println("Aviso: Falha ao geocodificar ou inserir a oficina: " + nome + ". Erro: " + e.getMessage());
        }
    } // <--- Fechamento correto do inserirOficina


    /**
     * Método auxiliar para criar e salvar um Serviço (Definido FORA de inserirOficina).
     */
    private void inserirServico(String nome, String descricao, BigDecimal preco, boolean destaque, Long oficinaId) {
        ServicoModel servico = new ServicoModel();
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setPrecoBase(preco);
        servico.setEmDestaque(destaque);

        // Busca a instância da Oficina pelo ID para associar o serviço
        servico.setOficina(oficinaRepository.findById(oficinaId).orElse(null));

        if (servico.getOficina() != null) {
            servicoRepository.save(servico);
        }
    }
}