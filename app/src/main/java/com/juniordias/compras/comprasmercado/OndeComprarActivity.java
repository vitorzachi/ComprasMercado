package com.juniordias.compras.comprasmercado;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.juniordias.compras.comprasmercado.tasks.BuscaLocaisProximosTask;
import com.juniordias.compras.comprasmercado.tasks.LocalProximo;

import java.util.ArrayList;
import java.util.List;

public class OndeComprarActivity extends FragmentActivity implements LocationListener {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private Location locationAtual;
    private boolean cameraZoomAplicado = false;
    private BuscaLocaisProximosTask task = new BuscaLocaisProximosTask(this);
    private List<LocalProximo> locaisProximos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onde_comprar);
        setUpMapIfNeeded();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        locationAtual = locationManager.getLastKnownLocation(provider);

        onLocationChanged(locationAtual);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        locationAtual = location;

        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.vc_esta_aqui))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            showMarkers(locaisProximos);

            if (!cameraZoomAplicado) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                cameraZoomAplicado = true;

                task.execute(locationAtual);
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void showMarkers(List<LocalProximo> localProximos) {
        this.locaisProximos = localProximos;
        LatLng latLng = new LatLng(locationAtual.getLatitude(), locationAtual.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.vc_esta_aqui))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        for (LocalProximo proximo : localProximos) {
            LatLng ll = new LatLng(proximo.getLatitude(), proximo.getLongitude());
            mMap.addMarker(new MarkerOptions().position(ll).title(proximo.getNome()));
        }
    }
}
