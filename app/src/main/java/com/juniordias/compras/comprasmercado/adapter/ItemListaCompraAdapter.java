package com.juniordias.compras.comprasmercado.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.juniordias.compras.comprasmercado.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jrdiaz on 04/07/2015.
 */
public class ItemListaCompraAdapter extends CursorAdapter {
private static final DecimalFormat DECIMAL_FORMAT=new DecimalFormat("#0.00");
    private LayoutInflater inflater;

    public ItemListaCompraAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        this.inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_da_lista, null);
        return item;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView lblNomeItem = (TextView) view.findViewById(R.id.lblNomeItem);
        TextView lblDescItem = (TextView) view.findViewById(R.id.lblDesItem);

        lblNomeItem.setText(cursor.getString(cursor.getColumnIndex("produto")));
        Double qtd = cursor.getDouble(cursor.getColumnIndex("qtde"));
        Double valor = cursor.getDouble(cursor.getColumnIndex("valorUnitario"));

        valor = valor == null ? 0d : valor;
        qtd = qtd == null ? 0d : qtd;

        lblDescItem.setText(String.format("%s X $ %s = $ %s", qtd, valor, DECIMAL_FORMAT.format(qtd*valor) ));
    }
}
