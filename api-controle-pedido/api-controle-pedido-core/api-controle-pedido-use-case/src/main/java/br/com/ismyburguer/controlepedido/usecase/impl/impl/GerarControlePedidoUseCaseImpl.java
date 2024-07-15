package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoRepository;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
public class GerarControlePedidoUseCaseImpl implements GerarControlePedidoUseCase {
    private final GerarControlePedidoRepository repository;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    private final ConsultarControlePedidoUseCase consultarControlePedidoUseCase;

    public GerarControlePedidoUseCaseImpl(GerarControlePedidoRepository repository,
                                          AlterarStatusPedidoUseCase alterarStatusPedidoUseCase,
                                          ConsultarControlePedidoUseCase consultarControlePedidoUseCase) {
        this.repository = repository;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
        this.consultarControlePedidoUseCase = consultarControlePedidoUseCase;
    }

    @Override
    @Transactional
    public UUID receberPedido(@Valid ControlePedido.PedidoId pedidoId) {
        ControlePedido consultar = consultarControlePedidoUseCase.consultar(pedidoId);
        if(consultar != null) {
            return consultar.getControlePedidoId().map(ControlePedido.ControlePedidoId::getControlePedidoId).orElse(null);
        }
        ControlePedido controlePedido = new ControlePedido(pedidoId);
        controlePedido.validate();
        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.RECEBIDO);
        return repository.gerar(controlePedido);
    }
}
