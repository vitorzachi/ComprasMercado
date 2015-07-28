package com.juniordias.compras.comprasmercado.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.juniordias.compras.comprasmercado.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jrdiaz on 04/07/2015.
 */
public class ListaCompraAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public ListaCompraAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        this.inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.lista_compras_item, null);
        return item;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView lblTitulo = (TextView) view.findViewById(R.id.lblNomeListaCompras);
        TextView lblDataCompra = (TextView) view.findViewById(R.id.lblDataCompra);
        TextView lblItens = (TextView) view.findViewById(R.id.lblItens);

        lblItens.setText(context.getString(R.string.totalItens, String.valueOf(cursor.getLong(cursor.getColumnIndex("contador")))));
        lblTitulo.setText(cursor.getString(cursor.getColumnIndex("titulo")));
        lblDataCompra.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(
                cursor.getLong(cursor.getColumnIndex("data")))));
    }
}
