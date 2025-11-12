package br.com.farias.icompras.faturamento.mapper;

import br.com.farias.icompras.faturamento.model.Cliente;
import br.com.farias.icompras.faturamento.model.ItemPedido;
import br.com.farias.icompras.faturamento.model.Pedido;
import br.com.farias.icompras.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import br.com.farias.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation representation){
        Cliente cliente = new Cliente(
                representation.nome(),
                representation.cpf(),
                representation.logradouro(),
                representation.numero(),
                representation.bairro(),
                representation.email(),
                representation.telefone()
        );

        List<ItemPedido> itens = representation.itens().stream().map(this::mapItem).toList();
        return new Pedido(representation.codigo(), cliente, representation.dataPedido(), representation.total(), itens);
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation representation){
        return new ItemPedido(
                representation.codigoProduto(),
                representation.nome(),
                representation.valorUnitario(),
                representation.quantidade(),
                representation.total()
                );
    }
}
