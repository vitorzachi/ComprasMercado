package com.juniordias.compras.comprasmercado.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juniordias.compras.comprasmercado.model.helper.ListaComprasOpenHelper;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;
import com.juniordias.compras.comprasmercado.model.vo.StatusLista;

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
        Cursor c = db.rawQuery("select count(l._id) from listacompras l", null);
        if (c.moveToNext() && c.getLong(0) > 0) {
            return db.rawQuery("select l._id, l.titulo, l.data, count(i._id) as contador " +
                    "from listacompras l left join itenscompras i on i.listaCompras=l._id group by l._id", null);
        }

        return db.query("listacompras", new String[]{"_id", "titulo", "data"}, null, null, null, null, null);
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
