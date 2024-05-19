package br.com.ismyburguer.controlepedido.adapters.repository;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoModelToControlePedidoConverter;
import br.com.ismyburguer.controlepedido.adapters.model.ControlePedidoModel;
import br.com.ismyburguer.controlepedido.adapters.model.StatusControlePedido;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarControlePedidoRepositoryImplTest {

    @Mock
    private ControlePedidoRepository controlePedidoRepository;

    @Mock
    private ControlePedidoModelToControlePedidoConverter converter;

    @InjectMocks
    private ListarControlePedidoRepositoryImpl listarControlePedidoRepository;

    @Test
    void listar_DeveRetornarListaDeControlePedidoOrdenadaPorDataDeRecebimento() {
        // Arrange
        ControlePedidoModel controlePedidoModel1 = new ControlePedidoModel();
        controlePedidoModel1.setRecebidoEm(LocalDateTime.of(2023, 5, 1, 10, 0));

        ControlePedidoModel controlePedidoModel2 = new ControlePedidoModel();
        controlePedidoModel2.setRecebidoEm(LocalDateTime.of(2023, 5, 1, 11, 0));

        List<ControlePedidoModel> controlePedidoModelList = List.of(controlePedidoModel1, controlePedidoModel2);

        ControlePedido controlePedido1 = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));
        ControlePedido controlePedido2 = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));

        when(controlePedidoRepository.findAllByStatusControlePedidoNot(StatusControlePedido.RETIRADO))
                .thenReturn(controlePedidoModelList);
        when(converter.convert(controlePedidoModel1)).thenReturn(controlePedido1);
        when(converter.convert(controlePedidoModel2)).thenReturn(controlePedido2);

        // Act
        List<ControlePedido> resultado = listarControlePedidoRepository.listar();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(controlePedido1, resultado.get(0));
        assertEquals(controlePedido2, resultado.get(1));
        verify(controlePedidoRepository, times(1)).findAllByStatusControlePedidoNot(StatusControlePedido.RETIRADO);
        verify(converter, times(1)).convert(controlePedidoModel1);
        verify(converter, times(1)).convert(controlePedidoModel2);
    }

    @Test
    void listar_DeveRetornarListaVaziaQuandoNaoHouverPedidos() {
        // Arrange
        when(controlePedidoRepository.findAllByStatusControlePedidoNot(StatusControlePedido.RETIRADO))
                .thenReturn(new ArrayList<>());

        // Act
        List<ControlePedido> resultado = listarControlePedidoRepository.listar();

        // Assert
        assertEquals(0, resultado.size());
        verify(controlePedidoRepository, times(1)).findAllByStatusControlePedidoNot(StatusControlePedido.RETIRADO);
        verifyNoInteractions(converter);
    }
}
