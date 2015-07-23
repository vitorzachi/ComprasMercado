package com.juniordias.compras.comprasmercado.model;

/**
 * Created by vitor on 22/07/15.
 */
public class Totais {
    private final Integer itens;
    private final Float total;

    public Totais(Integer itens, Float total) {
        this.itens = itens;
        this.total = total;
    }

    public Float getTotal() {
        return total;
    }

    public Integer getItens() {
        return itens;
    }
}
