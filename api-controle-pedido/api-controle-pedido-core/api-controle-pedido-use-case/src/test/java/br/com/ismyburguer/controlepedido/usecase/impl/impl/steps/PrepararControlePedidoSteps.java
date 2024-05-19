import br.com.ismyburguer.controlepedido.adapter.interfaces.in.ConsultarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.controlepedido.usecase.impl.impl.PrepararControlePedidoUseCaseImpl;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.validation.ConstraintViolationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrepararControlePedidoSteps {

    @Mock
    private AlterarControlePedidoRepository repository;

    @Mock
    private ConsultarControlePedidoUseCase controlePedidoUseCase;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @InjectMocks
    private PrepararControlePedidoUseCaseImpl prepararControlePedidoUseCase;

    private ControlePedido.PedidoId pedidoId;
    private ControlePedido controlePedido;
    private Exception excecao;
    private Pedido.StatusPedido statusPedidoAntes;

    @Given("que o controle do pedido está disponível")
    public void que_o_controle_do_pedido_esta_disponivel() {
        MockitoAnnotations.openMocks(this);
        pedidoId = new ControlePedido.PedidoId(UUID.randomUUID());
        controlePedido = mock(ControlePedido.class);
        when(controlePedido.getStatusControlePedido()).thenReturn(ControlePedido.StatusControlePedido.RECEBIDO);
        when(controlePedidoUseCase.consultar(pedidoId)).thenReturn(controlePedido);
    }

    @Given("que o controle do pedido está disponível mas inválido")
    public void que_o_controle_do_pedido_esta_disponivel_mas_invalido() {
        MockitoAnnotations.openMocks(this);
        pedidoId = new ControlePedido.PedidoId(UUID.randomUUID());
        controlePedido = mock(ControlePedido.class);
        when(controlePedido.getStatusControlePedido()).thenReturn(ControlePedido.StatusControlePedido.RECEBIDO);
        statusPedidoAntes = Pedido.StatusPedido.RECEBIDO;
        doThrow(new ConstraintViolationException(null)).when(controlePedido).validate();
        when(controlePedidoUseCase.consultar(pedidoId)).thenReturn(controlePedido);
    }

    @When("eu preparo o controle do pedido")
    public void eu_preparo_o_controle_do_pedido() {
        try {
            statusPedidoAntes = Pedido.StatusPedido.valueOf(controlePedido.getStatusControlePedido().name());
            prepararControlePedidoUseCase.preparar(pedidoId);
        } catch (Exception e) {
            excecao = e;
        }
    }
    @When("eu tento preparar o controle do pedido")
    public void eu_tento_preparar_o_controle_do_pedido() {
        try {
            prepararControlePedidoUseCase.preparar(pedidoId);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Then("o status do controle do pedido deve ser alterado para {string}")
    public void o_status_do_controle_do_pedido_deve_ser_alterado_para(String status) {
        verify(controlePedido, times(1)).emPreparacao();
        verify(repository, times(1)).alterar(controlePedido);
    }

    @Then("o status do pedido deve ser alterado para {string}")
    public void o_status_do_pedido_deve_ser_alterado_para(String status) {
        verify(alterarStatusPedidoUseCase, times(1)).alterar(new Pedido.PedidoId(pedidoId.getPedidoId()), Pedido.StatusPedido.valueOf(status));
    }

    @Then("uma exceção de validação deve ser lançada")
    public void uma_excecao_de_validacao_deve_ser_lancada() {
        assertNotNull(excecao);
        assertThrows(ConstraintViolationException.class, () -> { throw excecao; });
        verify(repository, never()).alterar(controlePedido);
        verify(alterarStatusPedidoUseCase, never()).alterar(any(), any());
    }

    @Then("o status do pedido não deve ser alterado")
    public void o_status_do_pedido_nao_deve_ser_alterado() {
        verify(alterarStatusPedidoUseCase, never()).alterar(any(), any());
        verify(controlePedido, never()).emPreparacao();
        verify(repository, never()).alterar(controlePedido);
        assertEquals(statusPedidoAntes.name(), controlePedido.getStatusControlePedido().name());
    }
}
