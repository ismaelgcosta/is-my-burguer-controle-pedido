package br.com.ismyburguer.controlepedido.adapters.converter;

import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControlePedidoToControlePedidoModelConverterTest {

    @Mock
    private ControlePedido controlePedido;

    @InjectMocks
    private ControlePedidoToControlePedidoModelConverter converter;

    @Test
    void convert_ControlePedidoParaControlePedidoModel_DeveRetornarControlePedidoModelCorretamente() {
        // Configuração do Mock ControlePedido
        UUID controlePedidoId = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        LocalDateTime recebidoEm = LocalDateTime.now();
        LocalDateTime inicioDaPreparacao = LocalDateTime.now();
        LocalDateTime fimDaPreparacao = LocalDateTime.now();
        ControlePedido.StatusControlePedido statusControlePedido = ControlePedido.StatusControlePedido.RECEBIDO;

        when(controlePedido.getControlePedidoId()).thenReturn(Optional.of(new ControlePedido.ControlePedidoId(controlePedidoId)));
        when(controlePedido.getPedidoId()).thenReturn(new ControlePedido.PedidoId(pedidoId));
        when(controlePedido.getRecebidoEm()).thenReturn(recebidoEm);
        when(controlePedido.getInicioDaPreparacao()).thenReturn(inicioDaPreparacao);
        when(controlePedido.getFimDaPreparacao()).thenReturn(fimDaPreparacao);
        when(controlePedido.getStatusControlePedido()).thenReturn(statusControlePedido);

        // Execução do método a ser testado
        ControlePedidoModel controlePedidoModel = converter.convert(controlePedido);

        // Verificação do resultado
        assertEquals(controlePedidoId, controlePedidoModel.getControlePedidoId());
        assertEquals(pedidoId, controlePedidoModel.getPedidoId());
        assertEquals(recebidoEm, controlePedidoModel.getRecebidoEm());
        assertEquals(inicioDaPreparacao, controlePedidoModel.getInicioDaPreparacao());
        assertEquals(fimDaPreparacao, controlePedidoModel.getFimDaPreparacao());
        assertEquals(statusControlePedido.name(), controlePedidoModel.getStatusControlePedido().name());
    }
}
