package br.com.farias.icompras.faturamento.publisher.representation;

public record AtualizacaoStatusPedido(
        long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
