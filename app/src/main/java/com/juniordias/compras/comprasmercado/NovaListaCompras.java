package com.juniordias.compras.comprasmercado;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.juniordias.compras.comprasmercado.model.dao.ListaComprasDAO;
import com.juniordias.compras.comprasmercado.model.vo.ListaCompras;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class NovaListaCompras extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_lista_compras);

        ((EditText) findViewById(R.id.edtDataCompra)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    onDateClick();
                }
            }
        });
    }

    public void salvar(View v) {
        //cria a variavel e armazena o que esta nos campos digitados pelo usuario
        EditText edtNomeLista = (EditText) findViewById(R.id.edtNomeLista);
        EditText edtDataCompra = (EditText) findViewById(R.id.edtDataCompra);

        //Instacia a ListaComprasVO e seta os dados na variavel
        ListaCompras listaCompras = new ListaCompras();
        listaCompras.setTitulo(edtNomeLista.getText().toString());

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(edtDataCompra.getText().toString());
            listaCompras.setData(date);
        } catch (ParseException e) {

        }
        //instancia o DAO e usa o metodo salvar para gravar os dados
        ListaComprasDAO listaComprasDAO = new ListaComprasDAO(this);
        listaComprasDAO.salvar(listaCompras);

        setResult(RESULT_OK);
        finish();
    }

    public void cancelar(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onDateClick() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setDatePickerFragment(this);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private NovaListaCompras novaListaCompras;

        public void setDatePickerFragment(NovaListaCompras novaListaCompras) {
            this.novaListaCompras = novaListaCompras;
        }

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
            EditText edtDataCompra = (EditText) novaListaCompras.findViewById(R.id.edtDataCompra);
            edtDataCompra.setText(String.format("%02d/%02d/%04d", day, month, year));
        }
    }
}
