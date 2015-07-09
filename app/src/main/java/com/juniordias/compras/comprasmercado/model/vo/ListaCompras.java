package com.juniordias.compras.comprasmercado.model.vo;

import java.util.Date;

/**
 * Created by Jrdiaz on 22/06/2015.
 */
public class ListaCompras {
    private Integer id;
    private String titulo;
    private Double valorTotal;
    private Date data;
    private String status;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
