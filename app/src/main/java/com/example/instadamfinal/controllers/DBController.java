package com.example.instadamfinal.controllers;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.instadamfinal.db.DataBaseHelper;
import com.example.instadamfinal.db.FirebaseDataBaseHelper;

import java.util.UUID;

/**
 * Clase que utilizamos como "puente" entre nuestra aplicacion/frontend y nuestras bases de datos (firebase y sql_lite), para no acceder desde las clases principales
 * de nuestra aplicacion.
 */

public class DBController {
    /**
     * Funcion que utilizamos para comprobar si el usuario y conntraseña coinciden en la base de datos y por tanto existe, en esta funcion
     * devolvemos como String el idUnico del usuario para trabajar con el.
     * @param emailUsuario el email introducido por el usuario en el Inicio de sesion.
     * @param passwordUsuario la contraseña introducida por el usuario en el Inicio de sesion.
     * @return devuelve el id en caso de que exista el usuario en la base de datos con ese e-mail y contraseña.
     */

    public String logearUsuarioController(Context context, String emailUsuario, String passwordUsuario){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.comprobarEmailPasswordUsuarioDB(emailUsuario,passwordUsuario);

    }

    /**Funcion que utilizamos para registrar el usuario en nuestra aplicacion, para ello verificamos si existe el usuario ya registrado
     * y si existe devolvemos false, sino continuamos con el registro de nuestro usuario.
     *
     * @param nombreUsuario el nombre de usuario que introduce el usuario cuando rellena el formulario de registro
     * @param emailUsuario el e-mail que introduce el usuario cuando rellena el formulario de registro
     * @param passwordUsuario contraseña introducida por el usuario durante el registro.
     * @return devolvemos false en caso de que exista el usuario o exista algun error durante el proceso de registro, sino hay error
     * el usuario se registra correctamente y devolvemos true.
     */
    public boolean registrarUsuario(Context context, String nombreUsuario, String emailUsuario, String passwordUsuario){

        boolean existeEmailUsuario = verificarExisteEmailUsuarioDB(context, emailUsuario);

        if (existeEmailUsuario)
            return false;//porque existe el email en la base de datos

        boolean resultadoCrearNuevoUsuario = crearNuevoUsuarioDB(context, nombreUsuario, emailUsuario, passwordUsuario);

        return resultadoCrearNuevoUsuario;
    }

    /**
     * Esta funcion es utilizada para verificar si el usuario ya existe mediante la verificacion del e-mail con la base de datos.
     *
     * @param emailUsuario E-mail que tenemos que verificar si existe en la base de datos.
     * @return devuelve un booleano en funcion de el resultado obtenido en la ejecucion del codigo.
     */
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