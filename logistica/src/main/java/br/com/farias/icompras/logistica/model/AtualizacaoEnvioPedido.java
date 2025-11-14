package br.com.farias.icompras.logistica.model;

public record AtualizacaoEnvioPedido(Long codigo, StatusPedido status, String codigoRastreio) {
}
