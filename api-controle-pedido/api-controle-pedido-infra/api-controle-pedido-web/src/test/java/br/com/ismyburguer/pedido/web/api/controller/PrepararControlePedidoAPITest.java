package br.com.ismyburguer.pedido.web.api.controller;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.PrepararControlePedidoUseCase;
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
public class PrepararControlePedidoAPITest {

    @Mock
    private PrepararControlePedidoUseCase useCase;

    @InjectMocks
    private PrepararControlePedidoAPI api;

    @Test
    void devePrepararPedidoComSucesso() {
        String pedidoId = "123e4567-e89b-12d3-a456-556642440000";

        // Configuração do comportamento do useCase.preparar()
        doNothing().when(useCase).preparar(any());

        // Chamada ao método que deve resultar em um preparo bem-sucedido do pedido
        api.prepararPedido(pedidoId);

        // Verifica se o método useCase.preparar() foi chamado corretamente
        verify(useCase, times(1)).preparar(new ControlePedido.PedidoId(pedidoId));
    }

    @Test
    void deveLancarResponseStatusExceptionQuandoPedidoIdForInvalido() {
        String pedidoId = "pedidoIdInvalido";

        // Verifica se o método lança uma ResponseStatusException quando o pedidoId é inválido
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> api.prepararPedido(pedidoId));

        // Verifica se o método useCase.preparar() foi chamado corretamente
        verify(useCase, times(0)).preparar(any());
        assertEquals("Invalid UUID string: pedidoIdInvalido", illegalArgumentException.getMessage());
    }
}
