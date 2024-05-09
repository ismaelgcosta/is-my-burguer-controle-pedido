package br.com.ismyburguer.controlepedido.adapters.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "controlePedidos")
@AllArgsConstructor
@NoArgsConstructor
public class ControlePedidoModel {

    @Id
    private UUID controlePedidoId = UUID.randomUUID();

    private UUID pedidoId;

    @Enumerated(EnumType.STRING)
    private StatusControlePedido statusControlePedido;

    private LocalDateTime recebidoEm = LocalDateTime.now();

    @Setter
    private LocalDateTime inicioDaPreparacao;

    @Setter
    private LocalDateTime fimDaPreparacao;

    public ControlePedidoModel(UUID pedidoId, StatusControlePedido statusControlePedido, LocalDateTime recebidoEm) {
        this.pedidoId = pedidoId;
        this.statusControlePedido = statusControlePedido;
        this.recebidoEm = recebidoEm;
    }

    public ControlePedidoModel(UUID controlePedidoId, UUID pedidoId, StatusControlePedido statusControlePedido, LocalDateTime recebidoEm) {
        this.controlePedidoId = controlePedidoId;
        this.pedidoId = pedidoId;
        this.statusControlePedido = statusControlePedido;
        this.recebidoEm = recebidoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ControlePedidoModel that)) return false;

        return new EqualsBuilder().append(getControlePedidoId(), that.getControlePedidoId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getControlePedidoId()).toHashCode();
    }
}
