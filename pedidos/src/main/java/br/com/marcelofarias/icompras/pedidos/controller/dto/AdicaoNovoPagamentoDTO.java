package br.com.marcelofarias.icompras.pedidos.controller.dto;

import br.com.marcelofarias.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dados, TipoPagamento tipoPagamento) {
}
