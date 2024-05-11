package br.com.ismyburguer.controlepedido.adapters.repository;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoToControlePedidoModelConverter;
import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GerarControlePedidoRepositoryImplTest {

    @Mock
    private ControlePedidoRepository controlePedidoRepository;

    @Mock
    private ControlePedidoToControlePedidoModelConverter converter;

    @InjectMocks
    private GerarControlePedidoRepositoryImpl gerarControlePedidoRepository;

    @Test
    void gerar_DeveRetornarIdGeradoAoSalvarControlePedido() {
        // Arrange
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));
        ControlePedidoModel controlePedidoModel = spy(new ControlePedidoModel());
        UUID controlePedidoId = UUID.randomUUID();

        when(converter.convert(controlePedido)).thenReturn(controlePedidoModel);
        when(controlePedidoRepository.save(controlePedidoModel)).thenReturn(controlePedidoModel);
        when(controlePedidoModel.getControlePedidoId()).thenReturn(controlePedidoId);

        // Act
        UUID resultado = gerarControlePedidoRepository.gerar(controlePedido);

        // Assert
        assertEquals(controlePedidoId, resultado);
        verify(converter, times(1)).convert(controlePedido);
        verify(controlePedidoRepository, times(1)).save(controlePedidoModel);
    }
}
