package br.com.ismyburguer.pedido.entity;


import br.com.ismyburguer.core.validation.Validation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido implements Validation {

    @Valid
    private PedidoId pedidoId;

    @Valid
    private ClienteId clienteId;

    @Getter
    private StatusPedido statusPedido = StatusPedido.ABERTO;

    @Setter
    private BigDecimal total;

    public Pedido(PedidoId pedidoId) {
        this.pedidoId = pedidoId;
    }

    @Getter
    public enum StatusPedido {

        ABERTO("Aberto"),
        FECHADO("Fechado"),
        AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
        PAGO("Pago"),
        PAGAMENTO_NAO_AUTORIZADO("Pagamento Não Autorizado"),
        RECEBIDO("Recebido"),
        CANCELADO("Recebido"),
        EM_PREPARACAO("Em Preparação"),
        PRONTO("Pronto"),
        FINALIZADO("Finalizado");

        private final String descricao;

        StatusPedido(String descricao) {
            this.descricao = descricao;
        }

    }

    @Getter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class PedidoId {

        @NotNull(message = "Informe o código do Pedido")
        private UUID pedidoId;

        public PedidoId(String pedidoId) {
            this.pedidoId = UUID.fromString(pedidoId);
        }
    }

    @Getter
    @EqualsAndHashCode
    public static class ClienteId {

        @NotNull(message = "Informe o código do Cliente")
        private UUID clienteId;

        public ClienteId(String clienteId) {
            this.clienteId = UUID.fromString(clienteId);
        }
    }

    public Optional<ClienteId> getClienteId() {
        return Optional.ofNullable(clienteId);
    }

    public Optional<PedidoId> getPedidoId() {
        return Optional.ofNullable(pedidoId);
    }
}
