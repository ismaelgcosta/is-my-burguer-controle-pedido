package br.com.ismyburguer.pedido.adapter.interfaces.in;

import br.com.ismyburguer.pedido.entity.Pedido;

public interface RetirarPedidoUseCase {
    void retirar(Pedido.PedidoId pedidoId);
}
