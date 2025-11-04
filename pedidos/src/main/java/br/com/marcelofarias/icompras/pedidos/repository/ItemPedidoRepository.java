package br.com.marcelofarias.icompras.pedidos.repository;

import br.com.marcelofarias.icompras.pedidos.model.ItemPedido;
import br.com.marcelofarias.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
