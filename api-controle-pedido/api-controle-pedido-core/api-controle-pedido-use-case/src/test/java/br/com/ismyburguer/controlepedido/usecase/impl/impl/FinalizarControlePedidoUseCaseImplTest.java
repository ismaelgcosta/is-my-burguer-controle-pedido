package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinalizarControlePedidoUseCaseImplTest {

    @Mock
    private AlterarControlePedidoRepository repository;

    @Mock
    private ConsultarControlePedidoUseCase consultarControlePedidoUseCase;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @InjectMocks
    private FinalizarControlePedidoUseCaseImpl useCase;

    @Test
    void deveFinalizarControlePedidoComSucesso() {
        // Preparação
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId("123e4567-e89b-12d3-a456-556642440000");
        ControlePedido controlePedido = new ControlePedido(pedidoId);
        controlePedido.emPreparacao();

        // Configuração do comportamento do mock
        when(consultarControlePedidoUseCase.consultar(pedidoId)).thenReturn(controlePedido);

        // Ação
        useCase.finalizar(pedidoId);

        // Verificação
        verify(consultarControlePedidoUseCase, times(1)).consultar(pedidoId);
        verify(repository, times(1)).alterar(controlePedido);
        verify(alterarStatusPedidoUseCase, times(1)).alterar(any(), any());
    }
}
