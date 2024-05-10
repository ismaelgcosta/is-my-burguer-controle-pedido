package br.com.ismyburguer.pedido.web.api.controller;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceberControlePedidoAPITest {

    @Mock
    private GerarControlePedidoUseCase useCase;

    @InjectMocks
    private ReceberControlePedidoAPI api;

    @Test
    void deveReceberPedidoComSucesso() {
        String pedidoId = "123e4567-e89b-12d3-a456-556642440000";

        // Chamada ao método que deve resultar em um recebimento bem-sucedido do pedido
        api.receberPedido(pedidoId);

        // Verifica se o método useCase.receberPedido() foi chamado corretamente
        verify(useCase, times(1)).receberPedido(new ControlePedido.PedidoId(pedidoId));
    }

    @Test
    void deveLancarResponseStatusExceptionQuandoPedidoIdForInvalido() {
        String pedidoId = "pedidoIdInvalido";

        // Verifica se o método lança uma ResponseStatusException quando o pedidoId é inválido
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> api.receberPedido(pedidoId));

        // Verifica se o método useCase.receberPedido() foi chamado corretamente
        verify(useCase, times(0)).receberPedido(any());
        assertEquals("Invalid UUID string: pedidoIdInvalido", illegalArgumentException.getMessage());
    }
}
