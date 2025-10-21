package com.example.tcc.cadastro_service;


import com.example.tcc.cadastro_model.CadastroModel;
import com.example.tcc.cadastro_model.ItemOrcamentoModel;
import com.example.tcc.cadastro_model.OficinaModel;
import com.example.tcc.cadastro_model.OrcamentoModel;
import com.example.tcc.cadastro_model.OrcamentoModel.Status; // Importa o ENUM Status
import com.example.tcc.cadastro_repository.CadastroRepository;
import com.example.tcc.cadastro_repository.OficinaRepository;
import com.example.tcc.cadastro_repository.OrcamentoRepository;
import com.example.tcc.cadastro_repository.ItemOrcamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para métodos que alteram o estado

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final ItemOrcamentoRepository itemOrcamentoRepository;
    private final CadastroRepository cadastroRepository; // Para buscar o Cliente
    private final OficinaRepository oficinaRepository;   // Para buscar a Oficina

    public OrcamentoService(OrcamentoRepository orcamentoRepository,
                            ItemOrcamentoRepository itemOrcamentoRepository,
                            CadastroRepository cadastroRepository,
                            OficinaRepository oficinaRepository) {
        this.orcamentoRepository = orcamentoRepository;
        this.itemOrcamentoRepository = itemOrcamentoRepository;
        this.cadastroRepository = cadastroRepository;
        this.oficinaRepository = oficinaRepository;
    }

    /**
     * 1. CRIA UM NOVO ORÇAMENTO
     * Este método receberá a solicitação do cliente ou da oficina e a salva.
     */
    @Transactional
    public OrcamentoModel criarOrcamento(Long clienteId, Long oficinaId, LocalDateTime dataDisponibilidade, List<ItemOrcamentoModel> itens) {

        // 1. Busca Cliente e Oficina
        CadastroModel cliente = cadastroRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        OficinaModel oficina = oficinaRepository.findById(oficinaId)
                .orElseThrow(() -> new RuntimeException("Oficina não encontrada."));

        // 2. Cria o Objeto Orçamento
        OrcamentoModel orcamento = new OrcamentoModel();
        orcamento.setCliente(cliente);
        orcamento.setOficina(oficina);
        orcamento.setDataDisponibilidade(dataDisponibilidade);
        orcamento.setStatus(Status.PENDENTE);

        // 3. Calcula o Valor Total
        // Soma o preço base de todos os itens
        BigDecimal valorTotal = itens.stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orcamento.setValorTotal(valorTotal);

        // 4. Salva o Orçamento (para obter o ID)
        OrcamentoModel orcamentoSalvo = orcamentoRepository.save(orcamento);

        // 5. Salva os Itens (associando ao ID do orçamento salvo)
        for (ItemOrcamentoModel item : itens) {
            item.setOrcamento(orcamentoSalvo);
            itemOrcamentoRepository.save(item);
        }

        return orcamentoSalvo;
    }

    /**
     * 2. BUSCA O HISTÓRICO DE ORÇAMENTOS POR CLIENTE
     */
    public List<OrcamentoModel> buscarHistorico(Long clienteId) {
        return orcamentoRepository.findByClienteId(clienteId);
    }

    /**
     * 3. ATUALIZA O STATUS PARA ACEITO
     */
    @Transactional
    public OrcamentoModel aceitarOrcamento(Long orcamentoId) {
        OrcamentoModel orcamento = orcamentoRepository.findById(orcamentoId)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado."));

        orcamento.setStatus(Status.ACEITO);
        return orcamentoRepository.save(orcamento);
    }

    /**
     * 4. ATUALIZA O STATUS PARA FINALIZADO
     */
    @Transactional
    public OrcamentoModel finalizarOrcamento(Long orcamentoId) {
        OrcamentoModel orcamento = orcamentoRepository.findById(orcamentoId)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado."));

        // Regra de Negócio: Só pode finalizar se estiver aceito
        if (orcamento.getStatus() != Status.ACEITO) {
            throw new IllegalStateException("Orçamento só pode ser finalizado se estiver ACEITO.");
        }

        orcamento.setStatus(Status.FINALIZADO);
        return orcamentoRepository.save(orcamento);
    }
}