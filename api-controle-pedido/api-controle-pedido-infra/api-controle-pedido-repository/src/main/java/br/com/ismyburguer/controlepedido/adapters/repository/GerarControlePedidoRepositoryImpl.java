package br.com.ismyburguer.controlepedido.adapters.repository;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoToControlePedidoModelConverter;
import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class GerarControlePedidoRepositoryImpl implements GerarControlePedidoRepository {
    private final ControlePedidoRepository controlepedidoRepository;
    private final ControlePedidoToControlePedidoModelConverter converter;

    public GerarControlePedidoRepositoryImpl(ControlePedidoRepository controlepedidoRepository,
                                             ControlePedidoToControlePedidoModelConverter converter) {
        this.controlepedidoRepository = controlepedidoRepository;
        this.converter = converter;
    }

    @Override
    public UUID gerar(ControlePedido controlePedido) {
        ControlePedidoModel controlepedidoModel = converter.convert(controlePedido);
        return controlepedidoRepository.save(controlepedidoModel).getControlePedidoId();
    }
}
