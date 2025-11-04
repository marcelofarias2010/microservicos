package br.com.marcelofarias.icompras.produtos.repository;

import br.com.marcelofarias.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
