package com.juniordias.compras.comprasmercado;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.juniordias.compras.comprasmercado.adapter.ListaCompraAdapter;
import com.juniordias.compras.comprasmercado.model.dao.ListaComprasDAO;
import com.juniordias.compras.comprasmercado.model.helper.ListaComprasOpenHelper;


public class Principal extends ActionBarActivity {
    private ListView listView;
    private ListaCompraAdapter adapter;
    private ListaComprasDAO listaComprasDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.listView = (ListView)findViewById(R.id.list);

        this.listaComprasDAO = new ListaComprasDAO(this);
        Cursor cursor = this.listaComprasDAO.listar();

        this.adapter = new ListaCompraAdapter(this, cursor);
        this.listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.NovaLista) {
            Intent intent = new Intent(this, NovaListaCompras.class);
            startActivityForResult(intent, 1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            adapter.changeCursor(this.listaComprasDAO.listar());
            adapter.notifyDataSetChanged();
        }
    }
}
