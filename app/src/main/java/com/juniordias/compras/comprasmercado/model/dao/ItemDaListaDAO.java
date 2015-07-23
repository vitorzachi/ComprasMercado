package com.juniordias.compras.comprasmercado.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juniordias.compras.comprasmercado.model.Totais;
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

        Cursor crs = db.query("itenscompras", new String[]{"_id", "produto", "qtde", "valorUnitario", "status"},
                " listacompras = ? ",
                new String[]{lista.getId().toString()}, null, null, " status asc");
        return crs;
    }

    public Totais totalizar(ListaCompras lista) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor crs = db.rawQuery("select count(_id), sum(qtde*valorUnitario) from itenscompras " +
                "where listaCompras = ?1", new String[]{lista.getId().toString()});
        crs.moveToNext();
        Totais totais = new Totais(crs.getInt(0), crs.getFloat(1));
        crs.close();
        db.close();
        return totais;
    }

    public void marcarFinalizado(Integer id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("status", StatusLista.Finalizada.name());

        db.update("itenscompras", valores, " _id=?", new String[]{id.toString()});
    }

    public void marcarNaoFinalizado(Integer id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("status", StatusLista.Aberta.name());

        db.update("itenscompras", valores, " _id=?", new String[]{id.toString()});
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

    public void apagar(Integer id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        long inserted = db.delete("itenscompras", " _id=?", new String[]{id.toString()});
        db.close();

    }

    public void alterar(ItensCompras item) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("produto", item.getProduto());
        valores.put("qtde", item.getQtde());
        valores.put("valorUnitario", item.getValorUnitario());
        valores.put("status", StatusLista.Aberta.name());

        long inserted = db.update("itenscompras", valores, " _id=?", new String[]{item.getId().toString()});
        db.close();

    }

    public ItensCompras findOne(Integer id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        ItensCompras item = null;
        final Cursor crs = db.query("itenscompras", null,
                " _id = ? ",
                new String[]{id.toString()}, null, null, null);

        if (crs.moveToNext()) {
            item = new ItensCompras();

            item.setValorUnitario(crs.getDouble(crs.getColumnIndex("valorUnitario")));
            item.setQtde(crs.getDouble(crs.getColumnIndex("qtde")));
            item.setProduto(crs.getString(crs.getColumnIndex("produto")));
            item.setListaCompras(new ListaCompras() {
                @Override
                public Integer getId() {
                    return (crs.getInt(crs.getColumnIndex("listacompras")));
                }
            });
            ;
            item.setStatus(crs.getString(crs.getColumnIndex("status")));
            item.setId(crs.getInt(crs.getColumnIndex("_id")));
        }

        return item;
    }
}
