package com.example.instadamfinal.models;

public class Publicacion {

    private String titulo;
    private String descripcion;

    private int numeroDeLikes;
    private String urlImagenPublicacion;

    public Publicacion() {
    }

    public Publicacion(int numeroDeLikes, String titulo,String descripcion, String urlImagenPublicacion) {
        this.numeroDeLikes = numeroDeLikes;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlImagenPublicacion = urlImagenPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroDeLikes() {
        return numeroDeLikes;
    }

    public void setNumeroDeLikes(int numeroDeLikes) {
        this.numeroDeLikes = numeroDeLikes;
    }

    public String getUrlImagenPublicacion() {
        return urlImagenPublicacion;
    }

    public void setUrlImagenPublicacion(String urlImagenPublicacion) {
        this.urlImagenPublicacion = urlImagenPublicacion;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "numeroDeLikes=" + numeroDeLikes +
                ", urlImagenPublicacion='" + urlImagenPublicacion + '\'' +
                '}';
    }
}
