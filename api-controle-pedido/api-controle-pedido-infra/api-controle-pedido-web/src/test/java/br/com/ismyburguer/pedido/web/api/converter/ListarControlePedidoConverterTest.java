package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.pedido.web.api.response.ListarControlePedidoResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListarControlePedidoConverterTest {

    @Test
    void deveConverterControlePedidoParaResponseComSucesso() throws InterruptedException {
        // Preparação do objeto ControlePedido
        UUID pedidoId = UUID.randomUUID();

        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(pedidoId));
        LocalDateTime recebidoEm = LocalDateTime.now();
        sleep(1000);

        LocalDateTime inicioPreparacao = LocalDateTime.now();
        controlePedido.emPreparacao();

        sleep(1000);
        LocalDateTime fimPreparacao = LocalDateTime.now();
        controlePedido.pronto();

        sleep(1000);
        controlePedido.retirado();

        // Conversão para ListarControlePedidoResponse
        ListarControlePedidoConverter converter = new ListarControlePedidoConverter();
        ListarControlePedidoResponse response = converter.convert(controlePedido);

        // Verificação dos campos convertidos
        assertEquals(pedidoId.toString(), response.getPedidoId());
        assertEquals("RETIRADO", response.getStatus());
        assertEquals(recebidoEm.withSecond(0).withNano(0), response.getRecebidoEm().withSecond(0).withNano(0));
        assertEquals(inicioPreparacao.withSecond(0).withNano(0), response.getInicioDaPreparacao().withSecond(0).withNano(0));
        assertEquals(fimPreparacao.withSecond(0).withNano(0), response.getFimDaPreparacao().withSecond(0).withNano(0));
    }

    @Test
    void deveLancarConstraintViolationExceptionQuandoControlePedidoForNulo() {
        // Preparação de um ControlePedido nulo
        ControlePedido controlePedido = null;

        // Tentativa de conversão para ListarControlePedidoResponse
        ListarControlePedidoConverter converter = new ListarControlePedidoConverter();

        // Verificação se a ConstraintViolationException é lançada ao converter um ControlePedido nulo
        assertThrows(ConstraintViolationException.class, () -> converter.convert(controlePedido));
    }
}
