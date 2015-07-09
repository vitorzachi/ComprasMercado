package com.juniordias.compras.comprasmercado.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.juniordias.compras.comprasmercado.R;

import java.util.zip.Inflater;

/**
 * Created by Jrdiaz on 04/07/2015.
 */
public class ListaCompraAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public ListaCompraAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        this.inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.lista_compras_item, null);
        return item;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView lblTitulo = (TextView) view.findViewById(R.id.lblNomeListaCompras);
        TextView lblDataCompra = (TextView) view.findViewById(R.id.lblDataCompra);

        lblTitulo.setText(cursor.getString(cursor.getColumnIndex("titulo")));
        lblDataCompra.setText(cursor.getString(cursor.getColumnIndex("data")));
    }
}
