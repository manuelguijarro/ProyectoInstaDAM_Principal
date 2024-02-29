package com.example.instadamfinal.listeners;

import com.example.instadamfinal.models.Usuario;
/**
 * Interfaz que la implementamos cuando necesitamos obtener el Objeto tipo Usuario de nuestra base de datos.
 */
public interface UsuarioListener {
    void onUsuarioListener(Usuario usuario);
}
