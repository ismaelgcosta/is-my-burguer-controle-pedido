package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RetirarPedidoUseCaseImplTest {

    @Mock
    private AlterarStatusPedidoUseCase pedidoUseCase;

    @InjectMocks
    private RetirarPedidoUseCaseImpl useCase;

    @Test
    void deveChamarMetodoAlterarPedidoComStatusFinalizado() {
        // Preparação
        Pedido.PedidoId pedidoId = new Pedido.PedidoId("123e4567-e89b-12d3-a456-556642440000");

        // Ação
        useCase.retirar(pedidoId);

        // Verificação
        verify(pedidoUseCase, times(1)).alterar(pedidoId, Pedido.StatusPedido.FINALIZADO);
    }
}
