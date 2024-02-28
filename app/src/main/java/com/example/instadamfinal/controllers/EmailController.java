package com.example.instadamfinal.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que utilizamos en nuestra App para verificar tanto que el email no esta vacio
 * como que contiene los caracteres necesario para ser un e-mail.
 */
public class EmailController {
    /**
     * Con este metodo chekeamos que el usuario a introducido un e-mail valido.
     * @param emailUsuario El email que introduce el usuario en nuestra app
     * @return devuelve si es un email valido para nuestra App o no.
     */
    public static boolean comprobarEmail(String emailUsuario){
        if(!emailUsuario.isEmpty()){

            String expresionRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
            Pattern pattern = Pattern.compile(expresionRegex);
            Matcher matcher = pattern.matcher(emailUsuario);
            return matcher.matches();

        }else
            return false;

    }
}
