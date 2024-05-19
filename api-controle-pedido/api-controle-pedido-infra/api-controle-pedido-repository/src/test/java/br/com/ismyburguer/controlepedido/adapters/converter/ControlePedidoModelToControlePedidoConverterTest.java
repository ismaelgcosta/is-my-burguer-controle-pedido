package br.com.ismyburguer.controlepedido.adapters.converter;

import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.adapters.model.StatusControlePedido;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ControlePedidoModelToControlePedidoConverterTest {

    @Test
    void deveConverterModelParaEntidadeComSucesso() {
        // Arrange
        UUID controlePedidoId = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        LocalDateTime recebidoEm = LocalDateTime.now();
        LocalDateTime inicioDaPreparacao = LocalDateTime.now();
        LocalDateTime fimDaPreparacao = LocalDateTime.now();

        ControlePedidoModel model = new ControlePedidoModel(
                controlePedidoId,
                pedidoId,
                StatusControlePedido.EM_PREPARACAO,
                recebidoEm,
                inicioDaPreparacao,
                fimDaPreparacao
        );

        ControlePedidoModelToControlePedidoConverter converter = new ControlePedidoModelToControlePedidoConverter();

        // Act
        ControlePedido result = converter.convert(model);

        // Assert
        assertNotNull(result);
        assertEquals(controlePedidoId, result.getControlePedidoId().get().getControlePedidoId());
        assertEquals(pedidoId, result.getPedidoId().getPedidoId());
        assertEquals(ControlePedido.StatusControlePedido.EM_PREPARACAO, result.getStatusControlePedido());
        assertEquals(recebidoEm, result.getRecebidoEm());
        assertEquals(inicioDaPreparacao, result.getInicioDaPreparacao());
        assertEquals(fimDaPreparacao, result.getFimDaPreparacao());
    }
}
