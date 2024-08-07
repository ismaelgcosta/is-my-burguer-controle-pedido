package br.com.ismyburguer.pedido.adapter.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRequestTest {

    @Test
    void run() {
        new PedidoRequest();
        UUID pedidoId = UUID.randomUUID();
        PedidoRequest pedidoRequest = new PedidoRequest(pedidoId, StatusPedidoRequest.RECEBIDO);
        assertEquals(pedidoRequest.getPedidoId(), pedidoId);
        assertEquals(pedidoRequest.getStatusPedido(), StatusPedidoRequest.RECEBIDO);
    }
}