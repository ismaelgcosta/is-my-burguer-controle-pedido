package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoRepository;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GerarControlePedidoUseCaseImplTest {

    @Mock
    private GerarControlePedidoRepository repository;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @Mock
    private ConsultarControlePedidoUseCase consultarControlePedidoUseCase;

    @InjectMocks
    private GerarControlePedidoUseCaseImpl useCase;

    @Test
    void receberPedido_ShouldReturnControlePedidoId_WhenPedidoExists() {
        // Arrange
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId(UUID.randomUUID().toString());
        ControlePedido controlePedido = new ControlePedido(pedidoId);
        controlePedido.setControlePedidoId(new ControlePedido.ControlePedidoId(UUID.randomUUID()));

        when(consultarControlePedidoUseCase.consultar(pedidoId)).thenReturn(controlePedido);

        // Act
        UUID result = useCase.receberPedido(pedidoId);

        // Assert
        assertNotNull(result);
        assertEquals(controlePedido.getControlePedidoId().get().getControlePedidoId(), result);
        verify(repository, never()).gerar(any(ControlePedido.class));
        verify(alterarStatusPedidoUseCase, never()).alterar(any(Pedido.PedidoId.class), any(Pedido.StatusPedido.class));
    }

    @Test
    void receberPedido_ShouldCreateControlePedido_WhenPedidoDoesNotExist() {
        // Arrange
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId(UUID.randomUUID().toString());
        ControlePedido controlePedido = new ControlePedido(pedidoId);
        UUID controlePedidoId = UUID.randomUUID();
        controlePedido.setControlePedidoId(new ControlePedido.ControlePedidoId(controlePedidoId));

        when(consultarControlePedidoUseCase.consultar(pedidoId)).thenThrow(new EntityNotFoundException("Pedido não encontrado"));
        when(repository.gerar(any())).thenReturn(controlePedidoId);

        // Act
        UUID result = useCase.receberPedido(pedidoId);

        // Assert
        assertNotNull(result);
        assertEquals(controlePedidoId, result);

        ArgumentCaptor<ControlePedido> controlePedidoCaptor = ArgumentCaptor.forClass(ControlePedido.class);
        verify(repository).gerar(controlePedidoCaptor.capture());
        assertEquals(pedidoId, controlePedidoCaptor.getValue().getPedidoId());

        verify(alterarStatusPedidoUseCase).alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.RECEBIDO);
    }
}
