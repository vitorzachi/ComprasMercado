package com.juniordias.compras.comprasmercado;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.juniordias.compras.comprasmercado.model.dao.ListaComprasDAO;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NovaListaCompras extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista_compras);
    }

    public void salvar(View v){
        //cria a variavel e armazena o que esta nos campos digitados pelo usuario
        EditText edtNomeLista = (EditText) findViewById(R.id.edtNomeLista);
        DatePicker edtDataCompra = (DatePicker) findViewById(R.id.datePicker);

        //Instacia a ListaComprasVO e seta os dados na variavel
        ListaCompras listaCompras = new ListaCompras();
        listaCompras.setTitulo(edtNomeLista.getText().toString());
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, edtDataCompra.getDayOfMonth());
        calendar.set(Calendar.MONTH, edtDataCompra.getMonth());
        calendar.set(Calendar.YEAR, edtDataCompra.getYear());
        listaCompras.setData(calendar.getTime());


        //instancia o DAO e usa o metodo salvar para gravar os dados
        ListaComprasDAO listaComprasDAO = new ListaComprasDAO(this);
        listaComprasDAO.salvar(listaCompras);

        setResult(RESULT_OK);
        finish();
    }

    public void onDateClick(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
}
