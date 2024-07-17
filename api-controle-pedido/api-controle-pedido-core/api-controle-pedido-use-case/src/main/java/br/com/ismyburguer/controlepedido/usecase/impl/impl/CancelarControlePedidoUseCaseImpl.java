package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.CancelarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class CancelarControlePedidoUseCaseImpl implements CancelarControlePedidoUseCase {
    private final AlterarControlePedidoRepository repository;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    private final ConsultarControlePedidoUseCase controlePedidoUseCase;

    public CancelarControlePedidoUseCaseImpl(AlterarControlePedidoRepository repository,
                                             AlterarStatusPedidoUseCase alterarStatusPedidoUseCase,
                                             ConsultarControlePedidoUseCase controlePedidoUseCase) {
        this.repository = repository;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
        this.controlePedidoUseCase = controlePedidoUseCase;
    }

    @Override
    @Transactional
    public void cancelarPedido(@Valid ControlePedido.PedidoId pedidoId) {
        ControlePedido controlePedido = controlePedidoUseCase.consultar(pedidoId);
        controlePedido.validate();
        controlePedido.cancelado();
        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.CANCELADO);
        repository.alterar(controlePedido);
    }
}
