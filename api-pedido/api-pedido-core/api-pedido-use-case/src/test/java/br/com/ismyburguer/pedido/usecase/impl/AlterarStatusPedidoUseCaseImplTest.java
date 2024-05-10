package br.com.ismyburguer.pedido.usecase.impl;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarStatusPedidoUseCaseImplTest {

    @Mock
    private AlterarStatusPedidoAPI statusPedidoAPI;

    @InjectMocks
    private AlterarStatusPedidoUseCaseImpl useCase;

    @Test
    void deveChamarMetodoAlterarAPI() {
        // Preparação
        Pedido.PedidoId pedidoId = new Pedido.PedidoId("123e4567-e89b-12d3-a456-556642440000");
        Pedido.StatusPedido statusPedido = Pedido.StatusPedido.PRONTO;

        // Ação
        useCase.alterar(pedidoId, statusPedido);

        // Verificação
        verify(statusPedidoAPI, times(1)).alterar(pedidoId, statusPedido);
    }
}
