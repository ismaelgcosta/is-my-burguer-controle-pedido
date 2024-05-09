package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.FinalizarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;

@UseCase
public class FinalizarControlePedidoUseCaseImpl implements FinalizarControlePedidoUseCase {
    private final AlterarControlePedidoRepository repository;
    private final ConsultarControlePedidoUseCase controlePedidoUseCase;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    public FinalizarControlePedidoUseCaseImpl(AlterarControlePedidoRepository repository,
                                              ConsultarControlePedidoUseCase controlePedidoUseCase,
                                              AlterarStatusPedidoUseCase alterarStatusPedidoUseCase) {
        this.repository = repository;
        this.controlePedidoUseCase = controlePedidoUseCase;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
    }

    @Override
    public void finalizar(ControlePedido.PedidoId pedidoId) {
        ControlePedido controlePedido = controlePedidoUseCase.consultar(pedidoId);
        controlePedido.validate();
        controlePedido.pronto();
        repository.alterar(controlePedido);
        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.PRONTO);
    }
}
