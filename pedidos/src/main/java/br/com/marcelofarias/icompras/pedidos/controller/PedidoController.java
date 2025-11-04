package br.com.marcelofarias.icompras.pedidos.controller;

import br.com.marcelofarias.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import br.com.marcelofarias.icompras.pedidos.controller.dto.NovoPedidoDTO;
import br.com.marcelofarias.icompras.pedidos.controller.mappers.PedidoMapper;
import br.com.marcelofarias.icompras.pedidos.model.ErroResposta;
import br.com.marcelofarias.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import br.com.marcelofarias.icompras.pedidos.model.exception.ValidationException;
import br.com.marcelofarias.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto) {
        try {
            var pedido = mapper.map(dto);
            var novoPedido = service.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO dto){
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
           var erro = new ErroResposta("Item não encontrado", "codigoPedido", e.getMessage());
           return ResponseEntity.badRequest().body(erro);
        }
    }
}
