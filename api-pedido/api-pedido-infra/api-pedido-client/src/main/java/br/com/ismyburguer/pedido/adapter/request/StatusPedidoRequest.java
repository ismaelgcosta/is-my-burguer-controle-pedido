package br.com.ismyburguer.pedido.adapter.request;

public enum StatusPedidoRequest {

    ABERTO,
    FECHADO,
    AGUARDANDO_PAGAMENTO,
    AGUARDANDO_CONFIRMACAO_PAGAMENTO,
    PAGO,
    CANCELADO,
    PAGAMENTO_NAO_AUTORIZADO,
    RECEBIDO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO;
}
