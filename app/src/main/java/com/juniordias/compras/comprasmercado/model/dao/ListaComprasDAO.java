package com.juniordias.compras.comprasmercado.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juniordias.compras.comprasmercado.model.helper.ListaComprasOpenHelper;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;

/**
 * Created by Jrdiaz on 04/07/2015.
 */
public class ListaComprasDAO {

    private ListaComprasOpenHelper helper;

    public ListaComprasDAO(Context context) {
        this.helper = new ListaComprasOpenHelper(context, "ListaFacilAPP", null, 1);
    }

    public Cursor listar() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor crs = db.query("listacompras", new String[]{"_id", "titulo", "data"}, null, null, null, null, null);
        return crs;
    }

    public void salvar(ListaCompras listaCompras) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("titulo", listaCompras.getTitulo());
        //valores.put("data", listaCompras.getData().toString());
        //valores.put("status", "Aberta");
        //valores.put("valorTotal", "0,00");
        db.insert("listacompras", null, valores);
        db.close();

    }
}
