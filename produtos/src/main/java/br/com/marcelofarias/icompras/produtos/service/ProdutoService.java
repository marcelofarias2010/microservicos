package br.com.marcelofarias.icompras.produtos.service;

import br.com.marcelofarias.icompras.produtos.model.Produto;
import br.com.marcelofarias.icompras.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor // cria um construtor com os parametros obrigatorios
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto salvar(Produto produto){
        return repository.save(produto);
    }

    public Optional<Produto> obterPorCodigo(Long codigo){
        return repository.findById(codigo);
    }

    public void deletar(Produto produto) {
        produto.setAtivo(false);
        repository.save(produto);
    }
}
