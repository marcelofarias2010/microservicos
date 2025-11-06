package br.com.marcelofarias.icompras.pedidos.service;

import br.com.marcelofarias.icompras.pedidos.client.ClientesClient;
import br.com.marcelofarias.icompras.pedidos.client.ProdutosClient;
import br.com.marcelofarias.icompras.pedidos.client.ServicoBancarioClient;
import br.com.marcelofarias.icompras.pedidos.model.DadosPagamento;
import br.com.marcelofarias.icompras.pedidos.model.ItemPedido;
import br.com.marcelofarias.icompras.pedidos.model.Pedido;
import br.com.marcelofarias.icompras.pedidos.model.enums.StatusPedido;
import br.com.marcelofarias.icompras.pedidos.model.enums.TipoPagamento;
import br.com.marcelofarias.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import br.com.marcelofarias.icompras.pedidos.repository.ItemPedidoRepository;
import br.com.marcelofarias.icompras.pedidos.repository.PedidoRepository;
import br.com.marcelofarias.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;

    @Transactional
    public Pedido criarPedido(Pedido pedido){
        validator.validar(pedido);        
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }


    public void atualizarStatusPagamento(Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido,chavePagamento);

        if(pedidoEncontrado.isEmpty()){
            var msg = String.format("Pedido não encontrado para o código %d e chave pgto %s", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        }

        Pedido pedido = pedidoEncontrado.get();

        if(sucesso){
            pedido.setStatus(StatusPedido.PAGO);
        }else{
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        repository.save(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo){
        var pedidoEncontrado = repository.findById(codigoPedido);

        if(pedidoEncontrado.isEmpty()){
            throw new ItemNaoEncontradoException("Pedido não encontrado para o código informado.");
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o novo processamento.");


        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);
        repository.save(pedido);
    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigo){
        Optional<Pedido> pedido = repository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        var response = apiClientes.obterDados(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    private void carregarItensPedido(Pedido pedido){
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private  void carregarDadosProduto(ItemPedido item){
        Long codigoProduto = item.getCodigoProduto();
        var response = apiProdutos.obterDados(codigoProduto);
        item.setNome(response.getBody().nome());
    }

}
