package com.example.instadamfinal.controllers;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.instadamfinal.db.DataBaseHelper;
import com.example.instadamfinal.db.FirebaseDataBaseHelper;

import java.util.UUID;

public class DBController {

    public String logearUsuarioController(Context context, String emailUsuario, String passwordUsuario){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.comprobarEmailPasswordUsuarioDB(emailUsuario,passwordUsuario);

    }

    public boolean registrarUsuario(Context context, String nombreUsuario, String emailUsuario, String passwordUsuario){

        boolean existeEmailUsuario = verificarExisteEmailUsuarioDB(context, emailUsuario);

        if (existeEmailUsuario)
            return false;//porque existe el email en la base de datos

        boolean resultadoCrearNuevoUsuario = crearNuevoUsuarioDB(context, nombreUsuario, emailUsuario, passwordUsuario);

        return resultadoCrearNuevoUsuario;
    }
    private boolean verificarExisteEmailUsuarioDB(Context context, String emailUsuario) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        boolean resultadoExisteUsuario = dataBaseHelper.verificarExisteEmailUsuarioHelper(emailUsuario);

        return resultadoExisteUsuario;
    }
    @SuppressLint("RestrictedApi")
    private boolean crearNuevoUsuarioDB(Context context, String nombreUsuario, String emailUsuario, String passwordUsuario) {

        try{
            String idUnico = UUID.randomUUID().toString();
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            boolean resultado = dataBaseHelper.crearNuevoUsuarioHelper(idUnico,nombreUsuario,emailUsuario,passwordUsuario);
            if (resultado){
                crearNuevoUsuarioFirebaseDB(idUnico,nombreUsuario,emailUsuario);
            }
            return resultado;

        }catch (Exception e){
            return false;
        }
    }
    private void crearNuevoUsuarioFirebaseDB(String idUnico, String nombreUsuario, String emailUsuario){
        FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
        firebaseDataBaseHelper.crearNuevoUsuarioFirebaseHelper(idUnico,nombreUsuario,emailUsuario);
    }
}