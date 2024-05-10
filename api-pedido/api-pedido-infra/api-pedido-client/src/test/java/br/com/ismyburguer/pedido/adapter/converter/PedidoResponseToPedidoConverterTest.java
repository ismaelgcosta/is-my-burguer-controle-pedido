package br.com.ismyburguer.pedido.adapter.converter;
import br.com.ismyburguer.pedido.adapter.response.PedidoResponse;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PedidoResponseToPedidoConverterTest {

    @Test
    void convert_DeveConverterPedidoResponseParaPedido_ComSucesso() {
        // Arrange
        PedidoResponse pedidoResponse = new PedidoResponse();
        String pedidoId = UUID.randomUUID().toString();
        pedidoResponse.setPedidoId(pedidoId);
        String clienteId = UUID.randomUUID().toString();
        pedidoResponse.setClienteId(clienteId);
        pedidoResponse.setStatus("FECHADO");
        pedidoResponse.setValorTotal(BigDecimal.valueOf(100.0));

        PedidoResponseToPedidoConverter converter = new PedidoResponseToPedidoConverter();

        // Act
        Pedido pedido = converter.convert(pedidoResponse);

        // Assert
        assertNotNull(pedido);
        assertEquals(pedidoId, pedido.getPedidoId().get().getPedidoId().toString());
        assertEquals(clienteId, pedido.getClienteId().get().getClienteId().toString());
        assertEquals(Pedido.StatusPedido.FECHADO, pedido.getStatusPedido());
        assertEquals(BigDecimal.valueOf(100.0), pedido.getTotal());
    }
}
