package br.com.ismyburguer.pedido.adapter.client;

import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.pedido.adapter.request.PedidoRequest;
import br.com.ismyburguer.pedido.adapter.request.StatusPedidoRequest;
import br.com.ismyburguer.pedido.entity.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.awspring.cloud.sqs.operations.SqsSendOptions;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarStatusPedidoAPIImplTest {

    @Mock
    private FeignClientAPI feignClientAPI;

    @Mock
    private SqsTemplate sqsTemplate;

    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Consumer<SqsSendOptions<Object>>> consumerArgumentCaptor;

    private AlterarStatusPedidoAPIImpl alterarStatusPedidoAPI;

    @BeforeEach
    void setUp() {
        objectMapper = spy(new ObjectMapper());
        alterarStatusPedidoAPI = new AlterarStatusPedidoAPIImpl(sqsTemplate, objectMapper);
    }

    @Test
    void alterar_DeveChamarAPI_QuandoPedidoExiste() {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido statusPedido = Pedido.StatusPedido.FECHADO;

        // Act
        alterarStatusPedidoAPI.alterar(pedidoId, statusPedido);

        verify(sqsTemplate, times(1)).send(consumerArgumentCaptor.capture());
    }

    @Test
    void alterar_ShouldSendMessageToQueue_WhenValidInputs() throws JsonProcessingException {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido statusPedido = Pedido.StatusPedido.RECEBIDO;
        PedidoRequest pedidoRequest = new PedidoRequest(pedidoId.getPedidoId(), StatusPedidoRequest.valueOf(statusPedido.name()));

        ObjectWriter writer = mock(ObjectWriter.class);
        lenient().when(objectMapper.writer()).thenReturn(writer);
        lenient().when(writer.withDefaultPrettyPrinter()).thenReturn(writer);
        lenient().when(writer.writeValueAsString(pedidoRequest)).thenReturn("json-string");

        // Act
        alterarStatusPedidoAPI.alterar(pedidoId, statusPedido);

        // Assert
        ArgumentCaptor<Consumer<SqsSendOptions<Object>>> captor = ArgumentCaptor.forClass(Consumer.class);
        verify(sqsTemplate).send(captor.capture());
    }

    @Test
    void alterar_ShouldThrowException_WhenJsonProcessingException() throws JsonProcessingException {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido statusPedido = Pedido.StatusPedido.RECEBIDO;

        ObjectWriter writer = mock(ObjectWriter.class);
        lenient().when(objectMapper.writer()).thenReturn(writer);
        lenient().when(writer.withDefaultPrettyPrinter()).thenReturn(writer);
        lenient().when(writer.writeValueAsString(any()))
                .thenThrow(new JsonProcessingException("Test exception") {
                });

        alterarStatusPedidoAPI.setPedidoQueue("test-queue-url");

        // Act
        alterarStatusPedidoAPI.alterar(pedidoId, statusPedido);

        // Assert
        ArgumentCaptor<Consumer<SqsSendOptions<Object>>> captor = ArgumentCaptor.forClass(Consumer.class);
        verify(sqsTemplate).send(captor.capture());

        Consumer<SqsSendOptions<Object>> sqsSendOptionsConsumer = captor.getValue();
        SqsSendOptions<Object> sqsSendOptions = mock(SqsSendOptions.class);
        when(sqsSendOptions.queue("test-queue-url")).thenReturn(sqsSendOptions);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                sqsSendOptionsConsumer.accept(sqsSendOptions)
        );

        verify(sqsSendOptions).queue("test-queue-url");

        assertEquals("Erro ao alterar status do pedido", thrown.getMessage());
    }

    @Test
    void alterar_ShouldThrowException_Success() throws JsonProcessingException {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido statusPedido = Pedido.StatusPedido.RECEBIDO;

        ObjectWriter writer = mock(ObjectWriter.class);
        lenient().when(objectMapper.writer()).thenReturn(writer);
        lenient().when(writer.withDefaultPrettyPrinter()).thenReturn(writer);
        lenient().when(writer.writeValueAsString(any())).thenReturn("json-string");

        alterarStatusPedidoAPI.setPedidoQueue("test-queue-url");

        // Act
        alterarStatusPedidoAPI.alterar(pedidoId, statusPedido);

        // Assert
        ArgumentCaptor<Consumer<SqsSendOptions<Object>>> captor = ArgumentCaptor.forClass(Consumer.class);
        verify(sqsTemplate).send(captor.capture());

        Consumer<SqsSendOptions<Object>> sqsSendOptionsConsumer = captor.getValue();
        SqsSendOptions<Object> sqsSendOptions = mock(SqsSendOptions.class);
        when(sqsSendOptions.queue("test-queue-url")).thenReturn(sqsSendOptions);

        // Act & Assert
        sqsSendOptionsConsumer.accept(sqsSendOptions);

        verify(sqsSendOptions).queue("test-queue-url");
    }
}
