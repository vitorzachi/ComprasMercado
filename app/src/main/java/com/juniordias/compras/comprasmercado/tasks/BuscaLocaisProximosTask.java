package com.juniordias.compras.comprasmercado.tasks;

import android.location.Location;
import android.os.AsyncTask;

import com.juniordias.compras.comprasmercado.OndeComprarActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitor on 22/07/15.
 */
public class BuscaLocaisProximosTask extends AsyncTask<Location, Void, List<LocalProximo>> {
    private final static String URL = "https://maps.googleapis.com/maps/api/place/" +
            "nearbysearch/json?key=AIzaSyDbKyd_wUbPzLrwxN5x2rrtZV0d_t1UnjQ&" +
            "location=%s,%s&rankby=distance&types=grocery_or_supermarket";
    private final OndeComprarActivity activity;

    public BuscaLocaisProximosTask(OndeComprarActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<LocalProximo> doInBackground(Location... params) {
        List<LocalProximo> locais = null;

        try {
            URL places = new URL(String.format(URL, params[0].getLatitude(), params[0].getLongitude()));
            URLConnection connection = places.openConnection();

            locais = obterLocais(new InputStreamReader(connection.getInputStream()));
        } catch (Exception e) {

        }
        return locais;
    }

    @Override
    protected void onPostExecute(List<LocalProximo> localProximos) {
        super.onPostExecute(localProximos);
        activity.showMarkers(localProximos);
    }

    private List<LocalProximo> obterLocais(InputStreamReader reader) {
        List<LocalProximo> locais = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(reader);

            StringBuffer retorno = new StringBuffer();
            String aux = null;
            do {
                aux = br.readLine();
                if (retorno != null) {
                    retorno.append(aux);
                }
            } while (aux != null);


            JSONObject ret = new JSONObject(retorno.toString());
            JSONArray results = ret.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject o = results.getJSONObject(i);
                String nome = o.getString("name");
                Double lat = o.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                Double lng = o.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                locais.add(new LocalProximo(nome, lat.floatValue(), lng.floatValue()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return locais;
    }
}
