package br.com.ismyburguer.pedido.adapter.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {

    UUID pedidoId;
    StatusPedidoRequest statusPedido;
}
