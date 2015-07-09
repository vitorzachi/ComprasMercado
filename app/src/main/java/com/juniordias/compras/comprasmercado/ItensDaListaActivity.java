package com.juniordias.compras.comprasmercado;

import android.app.ActionBar;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.juniordias.compras.comprasmercado.adapter.ItemListaCompraAdapter;
import com.juniordias.compras.comprasmercado.adapter.ListaCompraAdapter;
import com.juniordias.compras.comprasmercado.model.dao.ItemDaListaDAO;
import com.juniordias.compras.comprasmercado.model.dao.ListaComprasDAO;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;


public class ItensDaListaActivity extends ActionBarActivity {
    public static final String chaveLista = "LISTA_CORRENTE";
    private ListView listView;
    private ItemListaCompraAdapter adapter;
    private ListaCompras listaCompras;
    private ItemDaListaDAO listaComprasDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_da_lista);

        this.listView = (ListView) findViewById(R.id.list);
        this.listaCompras = (ListaCompras) getIntent().getSerializableExtra(chaveLista);
        this.listaComprasDAO = new ItemDaListaDAO(this);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(listaCompras.getTitulo());
        Cursor cursor = this.listaComprasDAO.listar(listaCompras);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_itens_da_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_novo) {
            Toast.makeText(this, "Sera implementado", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
