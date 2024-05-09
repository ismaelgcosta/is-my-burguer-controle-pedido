package br.com.ismyburguer.controlepedido.adapters.repository;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoModelToControlePedidoConverter;
import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.ConsultarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;

import java.util.Optional;
import java.util.UUID;

@PersistenceAdapter
public class ConsultarControlePedidoRepositoryImpl implements ConsultarControlePedidoRepository {
    private final ControlePedidoRepository controlePedidoRepository;
    private final ControlePedidoModelToControlePedidoConverter converter;

    public ConsultarControlePedidoRepositoryImpl(ControlePedidoRepository controlePedidoRepository,
                                                 ControlePedidoModelToControlePedidoConverter converter) {
        this.controlePedidoRepository = controlePedidoRepository;
        this.converter = converter;
    }

    @Override
    public Optional<ControlePedido> consultar(UUID pedidoId) {
        Optional<ControlePedidoModel> controlePedidoEntity = controlePedidoRepository.findByPedidoId(pedidoId);
        return controlePedidoEntity.map(converter::convert);
    }

}
