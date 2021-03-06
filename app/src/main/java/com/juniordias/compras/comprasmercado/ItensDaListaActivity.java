package com.juniordias.compras.comprasmercado;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.juniordias.compras.comprasmercado.adapter.ItemListaCompraAdapter;
import com.juniordias.compras.comprasmercado.dragAndDrop.SwipeDetector;
import com.juniordias.compras.comprasmercado.model.Totais;
import com.juniordias.compras.comprasmercado.model.dao.ItemDaListaDAO;
import com.juniordias.compras.comprasmercado.model.dao.ListaComprasDAO;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;

import java.text.DecimalFormat;


public class ItensDaListaActivity extends ActionBarActivity {
    public static final String chaveLista = "LISTA_CORRENTE";
    public static final String chaveItem = "ITEM_CORRENTE";
    private ListView listView;
    private ItemListaCompraAdapter adapter;
    private ListaCompras listaCompras;
    private ItemDaListaDAO itensComprasDAO;
    private ListaComprasDAO listaComprasDAO;
    private SwipeDetector swipeDetector = new SwipeDetector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_da_lista);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#69c2f9")));

        this.listView = (ListView) findViewById(R.id.list);
        this.listaCompras = (ListaCompras) getIntent().getSerializableExtra(chaveLista);
        this.itensComprasDAO = new ItemDaListaDAO(this);
        this.listaComprasDAO = new ListaComprasDAO(this);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(listaCompras.getTitulo());
        Cursor cursor = this.itensComprasDAO.listar(listaCompras);
        adapter = new ItemListaCompraAdapter(this, cursor);
        listView.setAdapter(adapter);

        listView.setOnTouchListener(swipeDetector);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                if (swipeDetector.swipeDetected()) {
                    if (swipeDetector.getAction() == SwipeDetector.Action.LR) {

                        itensComprasDAO.marcarFinalizado(Long.valueOf(id).intValue());
                        ItensDaListaActivity.this.refresh();
                        Toast.makeText(ItensDaListaActivity.this, getString(R.string.marcadoOk), Toast.LENGTH_SHORT).show();
                    }

                    if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        itensComprasDAO.marcarNaoFinalizado(Long.valueOf(id).intValue());
                        ItensDaListaActivity.this.refresh();
                        Toast.makeText(ItensDaListaActivity.this, getString(R.string.marcadoNaoOk), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Intent intent = new Intent(ItensDaListaActivity.this, NovoItemDaListaActivity.class);
                    intent.putExtra(ItensDaListaActivity.chaveItem, Long.valueOf(id).intValue());
                    ItensDaListaActivity.this.startActivityForResult(intent, 4);
                }
            }
        });

        atualizaTotais();
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
            Intent intent = new Intent(this, NovoItemDaListaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(chaveLista, listaCompras);
            intent.putExtras(bundle);

            startActivityForResult(intent, 4);
            return true;
        }
        if (id == R.id.action_excluir) {
            listaComprasDAO.apagar(listaCompras.getId());
            setResult(RESULT_OK);
            finish();
            return true;
        }

        if (id == R.id.action_onde) {
            Intent intent = new Intent(this, OndeComprarActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4) {
            refresh();
        }
    }

    public void refresh() {
        Cursor cursor = this.itensComprasDAO.listar(listaCompras);
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
        atualizaTotais();
    }

    private void atualizaTotais() {
        Totais totais = itensComprasDAO.totalizar(listaCompras);

        ((TextView) findViewById(R.id.tv_footer))
                .setText(getString(R.string.totalLista, totais.getItens(),
                        "$ " + new DecimalFormat("#0.00").format(totais.getTotal())));
    }
}
