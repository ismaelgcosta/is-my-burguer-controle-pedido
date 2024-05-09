package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.RetirarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.RetirarPedidoUseCase;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class RetirarControlePedidoUseCaseImpl implements RetirarControlePedidoUseCase {
    private final AlterarControlePedidoRepository repository;
    private final ConsultarControlePedidoUseCase controlePedidoUseCase;
    private final RetirarPedidoUseCase retirarPedidoUseCase;
    public RetirarControlePedidoUseCaseImpl(AlterarControlePedidoRepository repository,
                                            ConsultarControlePedidoUseCase controlePedidoUseCase,
                                            RetirarPedidoUseCase retirarPedidoUseCase) {
        this.repository = repository;
        this.controlePedidoUseCase = controlePedidoUseCase;
        this.retirarPedidoUseCase = retirarPedidoUseCase;
    }

    @Override
    @Transactional
    public void retirar(ControlePedido.PedidoId pedidoId) {
        ControlePedido controlePedido = controlePedidoUseCase.consultar(pedidoId);
        controlePedido.validate();
        controlePedido.retirado();
        repository.alterar(controlePedido);
        retirarPedidoUseCase.retirar(new Pedido.PedidoId(pedidoId.getPedidoId()));
    }
}
