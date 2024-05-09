package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.RetirarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;

@UseCase
public class RetirarPedidoUseCaseImpl implements RetirarPedidoUseCase {
    private final AlterarStatusPedidoUseCase pedidoUseCase;
    public RetirarPedidoUseCaseImpl(AlterarStatusPedidoUseCase pedidoUseCase) {
        this.pedidoUseCase = pedidoUseCase;
    }
    @Override
    public void retirar(Pedido.PedidoId pedidoId) {
        pedidoUseCase.alterar(pedidoId, Pedido.StatusPedido.FINALIZADO);
    }
}
