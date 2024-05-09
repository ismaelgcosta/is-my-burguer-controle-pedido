package br.com.ismyburguer.controlepedido.adapters.repository;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoModelToControlePedidoConverter;
import br.com.ismyburguer.controlepedido.adapters.model.StatusControlePedido;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.ListarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;

import java.util.Comparator;
import java.util.List;

@PersistenceAdapter
public class ListarControlePedidoRepositoryImpl implements ListarControlePedidoRepository {
    private final ControlePedidoRepository controlePedidoRepository;
    private final ControlePedidoModelToControlePedidoConverter converter;

    public ListarControlePedidoRepositoryImpl(ControlePedidoRepository controlePedidoRepository,
                                              ControlePedidoModelToControlePedidoConverter converter) {
        this.controlePedidoRepository = controlePedidoRepository;
        this.converter = converter;
    }

    @Override
    public List<ControlePedido> listar() {
        return controlePedidoRepository.findAllByStatusControlePedidoNot(StatusControlePedido.RETIRADO)
                .stream()
                .map(converter::convert)
                .sorted(Comparator.comparing(ControlePedido::getRecebidoEm))
                .toList();
    }
}
