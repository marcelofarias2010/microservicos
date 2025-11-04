package br.com.marcelofarias.icompras.pedidos.controller.mappers;

import br.com.marcelofarias.icompras.pedidos.controller.dto.ItemPedidoDTO;
import br.com.marcelofarias.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);
}
