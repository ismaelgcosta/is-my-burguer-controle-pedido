package br.com.ismyburguer.pedido.web.api.controller;

import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ListarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.pedido.web.api.converter.ListarControlePedidoConverter;
import br.com.ismyburguer.pedido.web.api.response.ListarControlePedidoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarControlePedidoAPITest {

    @Mock
    private ListarControlePedidoUseCase useCase;

    @Mock
    private ListarControlePedidoConverter converter;

    @InjectMocks
    private ListarControlePedidoAPI api;

    @Test
    void deveListarPedidosComSucesso() {
        // Simula o retorno do caso de uso
        List<ControlePedido> controlePedidos = List.of(new ControlePedido(
                new ControlePedido.PedidoId(UUID.randomUUID())
        ));
        when(useCase.listar()).thenReturn(controlePedidos);

        // Simula o retorno do conversor
        ListarControlePedidoResponse responses = new ListarControlePedidoResponse();
        when(converter.convert(any())).thenReturn(responses);

        // Chamada ao método que lista os pedidos
        List<ListarControlePedidoResponse> result = api.listar();

        // Verifica se o método do caso de uso foi chamado corretamente
        verify(useCase, times(1)).listar();

        // Verifica se o método do conversor foi chamado corretamente
        verify(converter, times(1)).convert(any());

        // Verifica se o resultado é o esperado
        assertEquals(List.of(responses), result);
    }
}
