package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultarPedidoUseCaseImplTest {

    @Mock
    private ConsultarPedidoAPI consultarPedidoAPI;

    @InjectMocks
    private ConsultarPedidoUseCaseImpl useCase;

    @Test
    void deveRetornarPedidoQuandoEncontrado() {
        // Preparação
        Pedido.PedidoId pedidoId = new Pedido.PedidoId("123e4567-e89b-12d3-a456-556642440000");
        Pedido pedido = new Pedido(pedidoId);

        // Configuração do comportamento do mock
        when(consultarPedidoAPI.obterPeloPedidoId(pedidoId.getPedidoId())).thenReturn(Optional.of(pedido));

        // Ação
        Pedido resultado = useCase.buscarPorId(pedidoId);

        // Verificação
        assertNotNull(resultado);
        assertEquals(pedido, resultado);
    }

    @Test
    void deveLancarEntityNotFoundExceptionQuandoPedidoNaoEncontrado() {
        // Preparação
        Pedido.PedidoId pedidoId = new Pedido.PedidoId("123e4567-e89b-12d3-a456-556642440000");

        // Configuração do comportamento do mock
        when(consultarPedidoAPI.obterPeloPedidoId(pedidoId.getPedidoId())).thenReturn(Optional.empty());

        // Verificação
        assertThrows(EntityNotFoundException.class, () -> useCase.buscarPorId(pedidoId));
    }
}
