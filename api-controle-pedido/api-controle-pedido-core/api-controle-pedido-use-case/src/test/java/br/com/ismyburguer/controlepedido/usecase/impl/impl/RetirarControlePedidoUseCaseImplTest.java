package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.RetirarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetirarControlePedidoUseCaseImplTest {

    @Mock
    private AlterarControlePedidoRepository repository;

    @Mock
    private ConsultarControlePedidoUseCase controlePedidoUseCase;

    @Mock
    private RetirarPedidoUseCase retirarPedidoUseCase;

    @InjectMocks
    private RetirarControlePedidoUseCaseImpl useCase;

    @Test
    @Transactional
    void deveRetirarControlePedidoComSucesso() {
        // Preparação
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId(UUID.randomUUID());
        ControlePedido controlePedido = new ControlePedido(pedidoId);

        // Configuração do comportamento do mock
        when(controlePedidoUseCase.consultar(pedidoId)).thenReturn(controlePedido);

        // Ação
        useCase.retirar(pedidoId);

        // Verificação
        verify(controlePedidoUseCase, times(1)).consultar(pedidoId);
        verify(repository, times(1)).alterar(controlePedido);
        verify(retirarPedidoUseCase, times(1)).retirar(new Pedido.PedidoId(pedidoId.getPedidoId()));
    }
}
