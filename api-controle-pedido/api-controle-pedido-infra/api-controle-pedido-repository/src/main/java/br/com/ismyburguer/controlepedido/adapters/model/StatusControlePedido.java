package br.com.ismyburguer.controlepedido.adapters.model;

import lombok.Getter;

@Getter
public enum StatusControlePedido {

    RECEBIDO("Recebido"),
    EM_PREPARACAO("Em Preparação"),
    PRONTO("Pronto"),
    CANCELADO("Cancelado"),
    RETIRADO("Retirado");

    private final String descricao;

    StatusControlePedido(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

