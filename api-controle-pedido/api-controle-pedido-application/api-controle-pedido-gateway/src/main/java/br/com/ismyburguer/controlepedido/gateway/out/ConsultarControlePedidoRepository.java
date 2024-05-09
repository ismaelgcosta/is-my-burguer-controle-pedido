package br.com.ismyburguer.controlepedido.gateway.out;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;

import java.util.Optional;
import java.util.UUID;

public interface ConsultarControlePedidoRepository {
    Optional<ControlePedido> consultar(UUID pedidoId);

}
