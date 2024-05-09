package br.com.ismyburguer.controlepedido.gateway.out;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;

import java.util.UUID;

public interface PrepararControlePedidoRepository {

    UUID preparar(ControlePedido controlepedido);

}
