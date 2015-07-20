package com.juniordias.compras.comprasmercado.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juniordias.compras.comprasmercado.model.vo.StatusLista;
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

    public void apagar(Integer id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        long itensDeletados = db.delete("itenscompras", " listacompras=?", new String[]{id.toString()});
        long listaDeletados = db.delete("listacompras", " _id=?", new String[]{id.toString()});
        db.close();

    }

    public void salvar(ListaCompras listaCompras) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("titulo", listaCompras.getTitulo());
        valores.put("data", listaCompras.getData().getTime());
        valores.put("status", StatusLista.Aberta.name());
        //valores.put("valorTotal", "0,00");
        db.insert("listacompras", null, valores);
        db.close();

    }
}
