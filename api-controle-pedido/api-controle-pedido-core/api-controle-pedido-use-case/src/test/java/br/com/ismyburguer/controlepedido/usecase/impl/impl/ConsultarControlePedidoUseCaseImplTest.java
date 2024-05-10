package br.com.ismyburguer.controlepedido.usecase.impl.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.ConsultarControlePedidoRepository;
import br.com.ismyburguer.core.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultarControlePedidoUseCaseImplTest {

    @Mock
    private ConsultarControlePedidoRepository repository;

    @InjectMocks
    private ConsultarControlePedidoUseCaseImpl useCase;

    @Test
    void deveRetornarControlePedidoQuandoEncontrado() {
        // Preparação
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId("123e4567-e89b-12d3-a456-556642440000");
        ControlePedido controlePedido = new ControlePedido(pedidoId);

        // Configuração do comportamento do mock
        when(repository.consultar(pedidoId.getPedidoId())).thenReturn(Optional.of(controlePedido));

        // Ação
        ControlePedido resultado = useCase.consultar(pedidoId);

        // Verificação
        assertNotNull(resultado);
        assertEquals(controlePedido, resultado);
    }

    @Test
    void deveLancarBusinessExceptionQuandoControlePedidoNaoEncontrado() {
        // Preparação
        ControlePedido.PedidoId pedidoId = new ControlePedido.PedidoId("123e4567-e89b-12d3-a456-556642440000");

        // Configuração do comportamento do mock
        when(repository.consultar(pedidoId.getPedidoId())).thenReturn(Optional.empty());

        // Verificação
        BusinessException exception = assertThrows(BusinessException.class, () -> useCase.consultar(pedidoId));
        assertEquals("O Pedido informado ainda não foi recebido", exception.getMessage());
    }
}
