package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.gateway.out.ConsultarControlePedidoRepository;
import br.com.ismyburguer.core.exception.BusinessException;
import br.com.ismyburguer.core.usecase.UseCase;

@UseCase
public class ConsultarControlePedidoUseCaseImpl implements ConsultarControlePedidoUseCase {
    private final ConsultarControlePedidoRepository repository;
    public ConsultarControlePedidoUseCaseImpl(ConsultarControlePedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ControlePedido consultar(ControlePedido.PedidoId pedidoId) {
        return repository.consultar(pedidoId.getPedidoId())
                .orElseThrow(() -> new BusinessException("O Pedido informado ainda não foi recebido"));
    }
}
