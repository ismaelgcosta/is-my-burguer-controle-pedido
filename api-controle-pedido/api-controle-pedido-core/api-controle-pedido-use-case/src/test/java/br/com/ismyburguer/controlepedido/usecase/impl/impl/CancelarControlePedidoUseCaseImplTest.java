package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import static org.junit.jupiter.api.Assertions.*;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarControlePedidoUseCaseImplTest {

    @Mock
    private AlterarControlePedidoRepository repository;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @Mock
    private ConsultarControlePedidoUseCase controlePedidoUseCase;

    @InjectMocks
    private CancelarControlePedidoUseCaseImpl cancelarControlePedidoUseCase;

    @Test
    void cancelarPedido_ShouldCancelOrderAndUpdateStatus_WhenOrderExists() {
        // Arrange
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId(UUID.randomUUID().toString());
        ControlePedido controlePedido = new ControlePedido(pedidoId);
        controlePedido.validate();

        when(controlePedidoUseCase.consultar(pedidoId)).thenReturn(controlePedido);

        // Act
        cancelarControlePedidoUseCase.cancelarPedido(pedidoId);

        // Assert
        verify(controlePedidoUseCase).consultar(pedidoId);
        verify(alterarStatusPedidoUseCase).alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.CANCELADO);

        ArgumentCaptor<ControlePedido> controlePedidoCaptor = ArgumentCaptor.forClass(ControlePedido.class);
        verify(repository).alterar(controlePedidoCaptor.capture());
        assertEquals(controlePedido, controlePedidoCaptor.getValue());
    }

    @Test
    void cancelarPedido_ShouldThrowException_WhenOrderDoesNotExist() {
        // Arrange
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId(UUID.randomUUID().toString());

        when(controlePedidoUseCase.consultar(pedidoId)).thenThrow(new EntityNotFoundException("Controle de pedido não encontrado"));

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () ->
                cancelarControlePedidoUseCase.cancelarPedido(pedidoId)
        );
        assertEquals("Controle de pedido não encontrado", thrown.getMessage());
    }
}
