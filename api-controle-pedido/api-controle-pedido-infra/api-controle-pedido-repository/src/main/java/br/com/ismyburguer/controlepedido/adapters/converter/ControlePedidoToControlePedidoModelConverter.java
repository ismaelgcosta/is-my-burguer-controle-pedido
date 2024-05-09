package br.com.ismyburguer.controlepedido.adapters.converter;

import br.com.ismyburguer.controlepedido.adapters.model.StatusControlePedido;
import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;

import java.util.UUID;

@PersistenceConverter
public class ControlePedidoToControlePedidoModelConverter implements Converter<ControlePedido, ControlePedidoModel> {
    @Override
    public ControlePedidoModel convert(ControlePedido source) {
        return new ControlePedidoModel(
                source.getControlePedidoId().map(ControlePedido.ControlePedidoId::getControlePedidoId).orElseGet(UUID::randomUUID),
                source.getPedidoId().getPedidoId(),
                StatusControlePedido.valueOf(source.getStatusControlePedido().name()),
                source.getRecebidoEm(),
                source.getInicioDaPreparacao(),
                source.getFimDaPreparacao()
        );
    }
}
