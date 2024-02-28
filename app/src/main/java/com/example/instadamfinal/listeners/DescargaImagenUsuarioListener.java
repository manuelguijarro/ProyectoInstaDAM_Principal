package com.example.instadamfinal.listeners;

import android.graphics.Bitmap;

/**
 * Interfaz que la implementamos cuando descargamos una imagen mediante firestorage, llamamos al metodo para obtener el resultado.
 */
public interface DescargaImagenUsuarioListener {
    void imagenDescargada(Bitmap bitmap);
}
