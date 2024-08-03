package br.com.ismyburguer.controlepedido.entity;

import br.com.ismyburguer.core.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ControlePedidoTest {

    @Test
    void deveCriarControlePedidoComSucesso() {
        // Preparação
        UUID pedidoId = UUID.randomUUID();

        // Ação
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(pedidoId));

        // Verificação
        assertNotNull(controlePedido);
        assertEquals(pedidoId.toString(), controlePedido.getPedidoId().getPedidoId().toString());
        assertEquals(ControlePedido.StatusControlePedido.RECEBIDO, controlePedido.getStatusControlePedido());
        assertNotNull(controlePedido.getRecebidoEm());
    }

    @Test
    void deveAtualizarParaEmPreparacaoComSucesso() {
        // Preparação
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));

        // Ação
        controlePedido.emPreparacao();

        // Verificação
        assertEquals(ControlePedido.StatusControlePedido.EM_PREPARACAO, controlePedido.getStatusControlePedido());
        assertNotNull(controlePedido.getInicioDaPreparacao());
    }

    @Test
    void deveAtualizarParaProntoComSucesso() {
        // Preparação
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));
        controlePedido.emPreparacao();

        // Ação
        controlePedido.pronto();

        // Verificação
        assertEquals(ControlePedido.StatusControlePedido.PRONTO, controlePedido.getStatusControlePedido());
        assertNotNull(controlePedido.getFimDaPreparacao());
    }

    @Test
    void deveLancarBusinessExceptionAoTentarAtualizarParaEmPreparacaoSemEstarRecebido() {
        // Preparação
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));
        controlePedido.emPreparacao();
        controlePedido.pronto();

        // Verificação
        BusinessException businessException = assertThrows(BusinessException.class, controlePedido::emPreparacao);
        assertEquals("O Pedido precisa estar com o Status Pronto para poder ser alterado para Em Preparação", businessException.getMessage());
    }

    @Test
    void deveLancarBusinessExceptionAoTentarAtualizarParaProntoSemEstarEmPreparacao() {
        // Preparação
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));

        // Verificação
        BusinessException businessException = assertThrows(BusinessException.class, controlePedido::pronto);
        assertEquals("O Pedido precisa estar com o Status Recebido para poder ser alterado para Pronto", businessException.getMessage());
    }

    @Test
    void deveAtualizarParaRetiradoComSucesso() {
        // Preparação
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));

        // Ação
        controlePedido.retirado();

        // Verificação
        assertEquals(ControlePedido.StatusControlePedido.RETIRADO, controlePedido.getStatusControlePedido());
    }
}
