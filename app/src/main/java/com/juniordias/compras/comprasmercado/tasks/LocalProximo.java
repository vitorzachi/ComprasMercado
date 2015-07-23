package com.juniordias.compras.comprasmercado.tasks;

/**
 * Created by vitor on 22/07/15.
 */
public class LocalProximo {
    private final String nome;
    private final float latitude, longitude;

    public LocalProximo(String nome, float latitude, float longitude) {
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getNome() {
        return nome;
    }
}
