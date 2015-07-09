package com.juniordias.compras.comprasmercado.model.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jrdiaz on 22/06/2015.
 */
public class ListaComprasOpenHelper extends SQLiteOpenHelper {

    public ListaComprasOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria o banco

        StringBuffer sql = new StringBuffer();
        sql.append("create table listacompras(");
        sql.append("_id integer primary key, ");
        sql.append("titulo Text, ");
        sql.append("valorTotal Double, ");
        sql.append("data Text, ");
        sql.append("status Text)");
        db.execSQL(sql.toString());

        StringBuffer sqlAtiv = new StringBuffer();
        sqlAtiv.append("create table itenscompras(");
        sqlAtiv.append("_id Integer primary key, ");
        sqlAtiv.append("produto Text, ");
        sqlAtiv.append("qtde Double, ");
        sqlAtiv.append("valorUnitario Double, ");
        sqlAtiv.append("status Text, ");
        sqlAtiv.append("listaCompras Integer References listacompras(_id) ) ");
        db.execSQL(sqlAtiv.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
