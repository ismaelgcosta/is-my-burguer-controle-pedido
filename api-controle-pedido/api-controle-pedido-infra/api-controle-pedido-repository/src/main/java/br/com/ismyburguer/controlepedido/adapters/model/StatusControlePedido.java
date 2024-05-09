package br.com.ismyburguer.controlepedido.adapters.model;

import lombok.Getter;

@Getter
public enum StatusControlePedido {

    RECEBIDO("Recebido"),
    EM_PREPARACAO("Em Preparação"),
    PRONTO("Pronto"),
    RETIRADO("Retirado");

    private final String descricao;

    StatusControlePedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

