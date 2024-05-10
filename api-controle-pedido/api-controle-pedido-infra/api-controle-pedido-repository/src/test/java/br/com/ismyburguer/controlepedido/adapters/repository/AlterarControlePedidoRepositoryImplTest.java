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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlterarControlePedidoRepositoryImplTest {

    @Mock
    private ControlePedidoRepository controlePedidoRepository;

    @Mock
    private ControlePedidoToControlePedidoModelConverter converter;

    @InjectMocks
    private AlterarControlePedidoRepositoryImpl repository;

    @Test
    public void alterar_DeveChamarSaveDoControlePedidoRepository() {
        // Arrange
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));
        ControlePedidoModel controlePedidoModel = new ControlePedidoModel();
        when(converter.convert(controlePedido)).thenReturn(controlePedidoModel);

        // Act
        repository.alterar(controlePedido);

        // Assert
        verify(converter).convert(controlePedido);
        verify(controlePedidoRepository).save(controlePedidoModel);
    }

    @Test
    public void alterar_DeveConverterControlePedidoParaControlePedidoModel() {
        // Arrange
        ControlePedido controlePedido = new ControlePedido(new ControlePedido.PedidoId(UUID.randomUUID()));
        ControlePedidoModel controlePedidoModel = new ControlePedidoModel();
        when(converter.convert(controlePedido)).thenReturn(controlePedidoModel);

        // Act
        repository.alterar(controlePedido);

        // Assert
        verify(converter).convert(controlePedido);
    }

}

