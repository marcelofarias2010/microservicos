package br.com.marcelofarias.icompras.pedidos.repository;

import br.com.marcelofarias.icompras.pedidos.model.ItemPedido;
import br.com.marcelofarias.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
