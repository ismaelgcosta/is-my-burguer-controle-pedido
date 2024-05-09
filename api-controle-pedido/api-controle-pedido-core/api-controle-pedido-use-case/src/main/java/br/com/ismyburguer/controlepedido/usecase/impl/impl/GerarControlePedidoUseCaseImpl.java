package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoRepository;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
public class GerarControlePedidoUseCaseImpl implements GerarControlePedidoUseCase {
    private final GerarControlePedidoRepository repository;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    public GerarControlePedidoUseCaseImpl(GerarControlePedidoRepository repository,
                                          AlterarStatusPedidoUseCase alterarStatusPedidoUseCase) {
        this.repository = repository;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
    }

    @Override
    @Transactional
    public UUID receberPedido(@Valid ControlePedido.PedidoId pedidoId) {
        ControlePedido controlePedido = new ControlePedido(pedidoId);
        controlePedido.validate();
        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.RECEBIDO);
        return repository.gerar(controlePedido);
    }
}
