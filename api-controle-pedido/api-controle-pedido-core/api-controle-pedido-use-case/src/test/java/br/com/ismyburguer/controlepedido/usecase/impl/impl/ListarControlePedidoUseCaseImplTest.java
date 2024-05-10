package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.ListarControlePedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarControlePedidoUseCaseImplTest {

    @Mock
    private ListarControlePedidoRepository repository;

    @InjectMocks
    private ListarControlePedidoUseCaseImpl useCase;

    @Test
    void deveListarControlePedidosComSucesso() {
        // Preparação
        List<ControlePedido> controlePedidos = Arrays.asList(
                new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID())),
                new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()))
        );

        // Configuração do comportamento do mock
        when(repository.listar()).thenReturn(controlePedidos);

        // Ação
        List<ControlePedido> resultado = useCase.listar();

        // Verificação
        assertEquals(controlePedidos, resultado);
        verify(repository, times(1)).listar();
    }
}
