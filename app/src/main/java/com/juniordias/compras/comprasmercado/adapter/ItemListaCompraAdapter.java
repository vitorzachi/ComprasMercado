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
public class ItemListaCompraAdapter extends CursorAdapter {

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
        lblDescItem.setText("2 X 2,99 = 5,98");
    }
}
