package com.example.instadamfinal.models;
/**
 * Clase modelo que representa una Publicacion de nuestra App.Lo utilizamos para añadir o obtener la publicacion mediante una Lista en la clase Usuario.
 */
public class Publicacion {

    private String titulo;
    private String descripcion;

    private int numeroDeLikes;
    private String urlImagenPublicacion;
    private String autorPublicacion;

    /**
     * Clase modelo que representa a cada publicacion que tiene asociada el usuario en su base de datos.
     */
    public Publicacion() {
    }

    public Publicacion(int numeroDeLikes, String titulo,String descripcion, String urlImagenPublicacion, String autorPublicacion) {
        this.autorPublicacion = autorPublicacion;
        this.numeroDeLikes = numeroDeLikes;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlImagenPublicacion = urlImagenPublicacion;
    }

    public String getAutorPublicacion() {
        return autorPublicacion;
    }

    public void setAutorPublicacion(String autorPublicacion) {
        this.autorPublicacion = autorPublicacion;
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
