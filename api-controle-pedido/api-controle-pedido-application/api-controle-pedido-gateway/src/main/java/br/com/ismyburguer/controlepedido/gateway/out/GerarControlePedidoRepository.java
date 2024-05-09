package br.com.ismyburguer.controlepedido.gateway.out;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;

import java.util.UUID;

public interface GerarControlePedidoRepository {

    UUID gerar(ControlePedido controlepedido);

}
