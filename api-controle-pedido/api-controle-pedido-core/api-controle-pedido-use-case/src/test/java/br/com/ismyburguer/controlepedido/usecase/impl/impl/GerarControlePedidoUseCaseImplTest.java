package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GerarControlePedidoUseCaseImplTest {

    @Mock
    private GerarControlePedidoRepository repository;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @InjectMocks
    private GerarControlePedidoUseCaseImpl useCase;

    @Test
    void deveGerarControlePedidoComSucesso() {
        // Preparação
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId(UUID.randomUUID().toString());

        // Ação
        useCase.receberPedido(pedidoId);

        // Verificação
        verify(alterarStatusPedidoUseCase, times(1)).alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.RECEBIDO);
        verify(repository, times(1)).gerar(any(ControlePedido.class));
    }

    @Test
    void deveLancarConstraintViolationExceptionQuandoPedidoIdForNulo() {
        // Ação e Verificação
        assertThrows(ConstraintViolationException.class, () -> useCase.receberPedido(null));
    }
}
