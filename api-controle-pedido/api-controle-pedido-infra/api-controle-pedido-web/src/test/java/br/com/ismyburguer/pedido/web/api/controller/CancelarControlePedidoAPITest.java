package br.com.ismyburguer.pedido.web.api.controller;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.CancelarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarControlePedidoAPITest {

    @Mock
    private CancelarControlePedidoUseCase cancelarControlePedidoUseCase;

    @InjectMocks
    private CancelarControlePedidoAPI cancelarControlePedidoAPI;

    @Test
    void cancelarPedido_ShouldCallUseCase_WhenPedidoIdIsValid() {
        // Arrange
        String pedidoId = "123e4567-e89b-12d3-a456-426614174000";

        // Act
        cancelarControlePedidoAPI.cancelarPedido(pedidoId);

        // Assert
        verify(cancelarControlePedidoUseCase).cancelarPedido(new ControlePedido.PedidoId(pedidoId));
    }

    @Test
    void cancelarPedido_ShouldHandleEntityNotFoundException_WhenPedidoDoesNotExist() {
        // Arrange
        String pedidoId = "123e4567-e89b-12d3-a456-426614174000";
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"))
                .when(cancelarControlePedidoUseCase).cancelarPedido(new ControlePedido.PedidoId(pedidoId));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cancelarControlePedidoAPI.cancelarPedido(pedidoId);
        });

        // Assert
        verify(cancelarControlePedidoUseCase).cancelarPedido(new ControlePedido.PedidoId(pedidoId));
    }
}
