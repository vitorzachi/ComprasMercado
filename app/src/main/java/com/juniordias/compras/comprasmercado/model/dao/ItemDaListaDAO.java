package com.juniordias.compras.comprasmercado.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juniordias.compras.comprasmercado.model.helper.ListaComprasOpenHelper;
import com.juniordias.compras.comprasmercado.model.vo.ItensCompras;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;
import com.juniordias.compras.comprasmercado.model.vo.StatusLista;

/**
 * Created by Jrdiaz on 04/07/2015.
 */
public class ItemDaListaDAO {

    private ListaComprasOpenHelper helper;

    public ItemDaListaDAO(Context context) {
        this.helper = new ListaComprasOpenHelper(context, "ListaFacilAPP", null, 1);
    }

    public Cursor listar(ListaCompras lista) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor crs = db.query("itenscompras", new String[]{"_id", "produto", "qtde", "valorUnitario"}, " listacompras = ? ",
                new String[]{lista.getId().toString()}, null, null, null);
        return crs;
    }

    public void salvar(ItensCompras item) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("produto", item.getProduto());
        valores.put("qtde", item.getQtde());
        valores.put("valorUnitario", item.getValorUnitario());
        valores.put("listaCompras", item.getListaCompras().getId());
        valores.put("status", StatusLista.Aberta.name());
        //valores.put("valorTotal", "0,00");
        long inserted = db.insert("itenscompras", null, valores);
        db.close();

    }
}
