package com.juniordias.compras.comprasmercado;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.juniordias.compras.comprasmercado.model.dao.ItemDaListaDAO;
import com.juniordias.compras.comprasmercado.model.vo.ItensCompras;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NovoItemDaListaActivity extends ActionBarActivity {
    private static final int REQUEST_CODE = 1234;
    private static ListaCompras listaCompras;
    private ItemDaListaDAO itemDaListaDAO;
    private Button btnVoz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item_da_lista);

        if ((ListaCompras) getIntent().getSerializableExtra(ItensDaListaActivity.chaveLista) != null) {
            listaCompras =(ListaCompras)getIntent().getSerializableExtra(ItensDaListaActivity.chaveLista);
        }

        itemDaListaDAO = new ItemDaListaDAO(this);
        btnVoz = (Button)findViewById(R.id.microfone);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            btnVoz.setEnabled(false);
            btnVoz.setVisibility(View.GONE);
        }
    }

    public void itensPorVoz(View v) {
        startVoiceRecognitionActivity();
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.vozHelp));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            if(matches.size()>0){
                String firstMatch = matches.get(0);
                String[] splitted = firstMatch.split("\\smais\\s|\\smas\\s");
                List<ItensCompras> itens = new ArrayList<>(splitted.length);
                for (String nome :splitted) {
                    ItensCompras item = new ItensCompras();
                    item.setListaCompras(listaCompras);
                    item.setProduto(nome);
                    item.setQtde(0d);
                    item.setValorUnitario(0d);
                    itens.add(item);
                }

                salvarItens(itens);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_novo_item_da_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_voz) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void salvar(View view) {
        ItensCompras item = new ItensCompras();
        item.setListaCompras(listaCompras);
        item.setProduto(((EditText) findViewById(R.id.nomeProduto)).getText().toString());
        try{
            item.setQtde(Double.valueOf(((EditText) findViewById(R.id.qtde)).getText().toString()));
        }catch (NumberFormatException e){

        }
        try{
            item.setValorUnitario(Double.valueOf(((EditText) findViewById(R.id.unitario)).getText().toString()));
        }catch (NumberFormatException e){

        }

        salvarItens(Arrays.asList(item));
    }

    private void salvarItens(List<ItensCompras> itens){
        for (ItensCompras item :itens) {
            itemDaListaDAO.salvar(item);
        }

        setResult(RESULT_OK);
        finish();
    }
}
