package br.com.ismyburguer.pedido.web.api.controller;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.FinalizarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinalizarControlePedidoAPITest {

    @Mock
    private FinalizarControlePedidoUseCase useCase;

    @InjectMocks
    private FinalizarControlePedidoAPI api;

    @Test
    void deveFinalizarPedidoComSucesso() {
        String pedidoId = "123e4567-e89b-12d3-a456-556642440000";

        // Configuração do comportamento do useCase.finalizar()
        doNothing().when(useCase).finalizar(new ControlePedido.PedidoId(pedidoId));

        // Chamada ao método que deve resultar em um pedido finalizado com sucesso
        api.pedidoPronto(pedidoId);

        // Verifica se o método useCase.finalizar() foi chamado corretamente
        verify(useCase, times(1)).finalizar(new ControlePedido.PedidoId(pedidoId));
    }

    @Test
    void deveLancarResponseStatusExceptionQuandoPedidoIdForInvalido() {
        String pedidoId = "pedidoIdInvalido";

        // Verifica se o método lança uma exceção quando o pedidoId é inválido
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> api.pedidoPronto(pedidoId));

        // Verifica se o método useCase.finalizar() não foi chamado
        verify(useCase, never()).finalizar(any());
        assertEquals("Invalid UUID string: pedidoIdInvalido", illegalArgumentException.getMessage());
    }
}
