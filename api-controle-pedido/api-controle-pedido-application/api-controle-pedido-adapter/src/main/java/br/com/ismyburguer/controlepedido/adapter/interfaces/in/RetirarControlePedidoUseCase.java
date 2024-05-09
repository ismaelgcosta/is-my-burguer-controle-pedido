package br.com.ismyburguer.controlepedido.adapter.interfaces.in;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;

public interface RetirarControlePedidoUseCase {

    void retirar(ControlePedido.PedidoId pedidoId);
}
