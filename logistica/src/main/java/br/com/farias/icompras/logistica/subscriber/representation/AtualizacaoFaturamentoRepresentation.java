package br.com.farias.icompras.logistica.subscriber.representation;

import br.com.farias.icompras.logistica.model.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
