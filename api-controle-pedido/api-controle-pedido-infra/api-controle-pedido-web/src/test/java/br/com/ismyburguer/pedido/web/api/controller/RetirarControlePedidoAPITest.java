package br.com.ismyburguer.pedido.web.api.controller;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.RetirarControlePedidoUseCase;
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
public class RetirarControlePedidoAPITest {

    @Mock
    private RetirarControlePedidoUseCase useCase;

    @InjectMocks
    private RetirarControlePedidoAPI api;

    @Test
    void deveRetirarPedidoComSucesso() {
        String pedidoId = "123e4567-e89b-12d3-a456-556642440000";

        // Configuração do comportamento do useCase.retirar()
        doNothing().when(useCase).retirar(new ControlePedido.PedidoId(pedidoId));

        // Chamada ao método que deve resultar em uma retirada bem-sucedida do pedido
        api.retirarPedido(pedidoId);

        // Verifica se o método useCase.retirar() foi chamado corretamente
        verify(useCase, times(1)).retirar(new ControlePedido.PedidoId(pedidoId));
    }

    @Test
    void deveLancarResponseStatusExceptionQuandoPedidoIdForInvalido() {
        String pedidoId = "pedidoIdInvalido";

        // Verifica se o método lança uma ResponseStatusException quando o pedidoId é inválido
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> api.retirarPedido(pedidoId));

        // Verifica se o método useCase.retirar() foi chamado corretamente
        verify(useCase, times(0)).retirar(any());
        assertEquals("Invalid UUID string: pedidoIdInvalido", illegalArgumentException.getMessage());
    }
}
