package br.com.ismyburguer.controlepedido.adapters.repository;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoToControlePedidoModelConverter;
import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import org.springframework.validation.annotation.Validated;

@Validated
@PersistenceAdapter
public class AlterarControlePedidoRepositoryImpl implements AlterarControlePedidoRepository {
    private final ControlePedidoRepository controlepedidoRepository;
    private final ControlePedidoToControlePedidoModelConverter converter;

    public AlterarControlePedidoRepositoryImpl(ControlePedidoRepository controlepedidoRepository,
                                               ControlePedidoToControlePedidoModelConverter converter) {
        this.controlepedidoRepository = controlepedidoRepository;
        this.converter = converter;
    }

    @Override
    public void alterar(ControlePedido controlepedido) {
        ControlePedidoModel controlePedidoModel = converter.convert(controlepedido);
        controlepedidoRepository.save(controlePedidoModel);
    }
}
