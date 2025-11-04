package br.com.marcelofarias.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "br.com.marcelofarias.icompras.pedidos.client")
public class ClientsConfig {

}
