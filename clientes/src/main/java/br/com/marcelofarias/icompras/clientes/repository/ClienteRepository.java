package br.com.marcelofarias.icompras.clientes.repository;

import br.com.marcelofarias.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
