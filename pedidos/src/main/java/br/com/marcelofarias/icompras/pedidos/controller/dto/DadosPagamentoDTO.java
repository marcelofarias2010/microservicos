package br.com.marcelofarias.icompras.pedidos.controller.dto;

import br.com.marcelofarias.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipoPagamento) {
}
