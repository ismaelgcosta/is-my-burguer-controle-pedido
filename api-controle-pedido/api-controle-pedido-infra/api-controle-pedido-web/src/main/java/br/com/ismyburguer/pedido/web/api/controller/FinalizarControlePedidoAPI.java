package br.com.ismyburguer.pedido.web.api.controller;


import br.com.ismyburguer.controlepedido.adapter.interfaces.in.FinalizarControlePedidoUseCase;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.core.adapter.in.WebAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Controle de Pedidos", description = "Controle de Pedidos")
@WebAdapter
@RequestMapping("/controle-pedidos")
public class FinalizarControlePedidoAPI {
    private final FinalizarControlePedidoUseCase useCase;

    public FinalizarControlePedidoAPI(FinalizarControlePedidoUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), method = "Pedido Pronto", description = "Pedido Pronto")
    @PutMapping("/{pedidoId}/pronto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pedidoPronto(
            @PathVariable @Valid @UUID(message = "O código do pedido informado está num formato inválido") String pedidoId
    ) {
        useCase.finalizar(new ControlePedido.PedidoId(java.util.UUID.fromString(pedidoId)));
    }

}
