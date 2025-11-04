package br.com.marcelofarias.icompras.pedidos.model;

import br.com.marcelofarias.icompras.pedidos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipoPagamento;
}
