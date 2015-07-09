package com.juniordias.compras.comprasmercado.model.vo;

/**
 * Created by Jrdiaz on 22/06/2015.
 */
public class ItensCompras {
    private Integer _id;
    private String produto;
    private Double qtde;
    private Double valorUnitario;
    private String status;
    private ListaCompras listaCompras;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Double getQtde() {
        return qtde;
    }

    public void setQtde(Double qtde) {
        this.qtde = qtde;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return (qtde==null?0d:qtde) * (valorUnitario==null?0d:valorUnitario);
    }


    public ListaCompras getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(ListaCompras listaCompras) {
        this.listaCompras = listaCompras;
    }
}
