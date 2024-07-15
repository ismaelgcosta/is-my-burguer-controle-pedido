package br.com.ismyburguer.controlepedido.adapter.interfaces.in;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CancelarControlePedidoUseCase {
    void cancelarPedido(@Valid @NotNull(message = "Informe o pedido") ControlePedido.PedidoId pedidoId);
}
