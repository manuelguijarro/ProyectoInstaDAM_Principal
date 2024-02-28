package com.example.instadamfinal.listeners;
/**
 * Interfaz que la implementamos cuando subimos una imagen mediante firestorage, llamamos a los metodos para obtener el resultado.
 */
public interface SubirImagenUsuarioListener {
    void imagenSubida() throws InterruptedException;
    void imagenFalloSubida();
}
