package com.example.instadamfinal.controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que uitlizamos tanto para verificar que la contraseña cumpla con unos requisitos minimos, como para cifrar la contraseña.
 */
public class PasswordController {
    /**
     *
     * Metodo que utilizamos para cifrar la contraseña del usuario antes de mandarla a la base de datos.
     */
    public static  String get_SHA_512_SecurePassword(String passwordToHash, String salt){

        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Con este metodo comprobamos que la contraseña no este vacía y que cumpla con unos requisitos minimos.
     */

    public static boolean comprobarPassword(String passwordUsuario) {
        if (!passwordUsuario.isEmpty()){

            String expresionRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+=<>?]).{8,}$";
            Pattern pattern = Pattern.compile(expresionRegex);
            Matcher matcher = pattern.matcher(passwordUsuario);
            return matcher.matches();

        }else
            return false;

    }
}
