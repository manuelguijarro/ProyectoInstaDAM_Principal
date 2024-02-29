package com.example.instadamfinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.instadamfinal.controllers.PasswordController;

/**
 * Clase que utilizamos para conectar con nuestra base de datos sql_lite, de esta manera tenemos una clase encapsulada, y accedemos a sus metodos a traves de ella.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper (@Nullable Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StructureDB.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(StructureDB.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     *Metodo para comprobar si la contraseña y el email introducidos por el usuario coinciden con el de la base de datos,
     * si coinciden devolverá el id_unico asociado al usuario.
     */
    public String comprobarEmailPasswordUsuarioDB(String emailUsuario, String passwordUsuario) {
        String idUnico = "";

        String passwordUsuarioCifrada = PasswordController.get_SHA_512_SecurePassword(passwordUsuario, "ambgk");
        Cursor cursor = this.getReadableDatabase().query(
                StructureDB.PERSONAL_DATA_TABLE,
                new String[]{StructureDB.COLUMN_USERNAME, StructureDB.COLUMN_EMAIL, StructureDB.COLUMN_PASS, StructureDB.COLUMN_IDUNICO},
                StructureDB.COLUMN_EMAIL + " = ? AND " + StructureDB.COLUMN_PASS + " = ?",
                new String[]{emailUsuario, passwordUsuarioCifrada},
                null,
                null,
                StructureDB.COLUMN_EMAIL + " DESC"
        );

        if (cursor.moveToFirst()) {
            // Se encontró un usuario con el correo electrónico y la contraseña proporcionados
            idUnico = cursor.getString(cursor.getColumnIndexOrThrow(StructureDB.COLUMN_IDUNICO));
        }

        cursor.close();

        return idUnico;

    }

    /**
     *Metodo para verificar si existe el email en la base de datos, devolvera true o false en funcion del resultado de la query.
     */
    public boolean verificarExisteEmailUsuarioHelper(String emailUsuario){
        Cursor cursor = this.getReadableDatabase().query(
                StructureDB.PERSONAL_DATA_TABLE,
                new String[]{StructureDB.COLUMN_EMAIL},
                StructureDB.COLUMN_EMAIL+ " = ?",
                new String[]{emailUsuario},
                null,
                null,
                StructureDB.COLUMN_EMAIL + " DESC"
        );

        int resultadoCantidadEmail = cursor.getCount();

        cursor.close();

        return  resultadoCantidadEmail > 0;

    }

    /**
     *Con este metodo, creariamos un usuario en nuestra base de datos, es el metodo que crea el usuario en sql_lite.
     */
    public boolean crearNuevoUsuarioHelper(String idUnico,String nombreUsuario, String emailUsuario, String passwordUsuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //Aqui ciframos la contraseña
        String passwordUsuarioCifrada = PasswordController.get_SHA_512_SecurePassword(passwordUsuario,"ambgk");
        values.put(StructureDB.COLUMN_USERNAME,nombreUsuario);
        values.put(StructureDB.COLUMN_EMAIL,emailUsuario);
        values.put(StructureDB.COLUMN_PASS,passwordUsuarioCifrada);
        values.put(StructureDB.COLUMN_IDUNICO,idUnico);
        long numID = db.insert(StructureDB.PERSONAL_DATA_TABLE,null,values);
        return numID != -1;
    }
}

