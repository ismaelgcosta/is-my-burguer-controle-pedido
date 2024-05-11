package br.com.ismyburguer.controlepedido.adapters.repository;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoModelToControlePedidoConverter;
import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarControlePedidoRepositoryImplTest {

    @Mock
    private ControlePedidoRepository controlePedidoRepository;

    @Mock
    private ControlePedidoModelToControlePedidoConverter converter;

    @InjectMocks
    private ConsultarControlePedidoRepositoryImpl consultarControlePedidoRepository;

    @Test
    void consultar_DeveRetornarControlePedidoQuandoExistirNaBase() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        ControlePedidoModel controlePedidoModel = new ControlePedidoModel();
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));

        when(controlePedidoRepository.findByPedidoId(pedidoId)).thenReturn(Optional.of(controlePedidoModel));
        when(converter.convert(controlePedidoModel)).thenReturn(controlePedido);

        // Act
        Optional<ControlePedido> resultado = consultarControlePedidoRepository.consultar(pedidoId);

        // Assert
        assertEquals(controlePedido, resultado.orElse(null));
    }

    @Test
    void consultar_DeveRetornarVazioQuandoNaoExistirNaBase() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();

        when(controlePedidoRepository.findByPedidoId(pedidoId)).thenReturn(Optional.empty());

        // Act
        Optional<ControlePedido> resultado = consultarControlePedidoRepository.consultar(pedidoId);

        // Assert
        assertEquals(Optional.empty(), resultado);
    }
}
