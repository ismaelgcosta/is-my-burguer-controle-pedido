package br.com.ismyburguer.controlepedido.adapters.converter;

import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;

@PersistenceConverter
public class ControlePedidoModelToControlePedidoConverter implements Converter<ControlePedidoModel, ControlePedido> {
    @Override
    public ControlePedido convert(ControlePedidoModel source) {
        return new ControlePedido(
                new ControlePedido.ControlePedidoId(source.getControlePedidoId()),
                new ControlePedido.PedidoId(source.getPedidoId()),
                ControlePedido.StatusControlePedido.valueOf(source.getStatusControlePedido().name()),
                source.getRecebidoEm(),
                source.getInicioDaPreparacao(),
                source.getFimDaPreparacao()
        );
    }
}
