package com.example.instadamfinal.controllers;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.instadamfinal.listeners.DescargaImagenUsuarioListener;
import com.example.instadamfinal.listeners.SubirImagenUsuarioListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Clase controladora que utilizamos para reutilizar los metodos que trabajan con
 * nuestro backend de firestorage, con esta clase implementamos de manera
 * asincrona la descarga/as de Imagenes y la subida de imagen.
 */

public class FireStorageController {

    /**
     * Metodo utiñizado para subir la imagen a el servidor de firestorage.
     * @param context
     * @param imagenNombre el nombre que tendra nuestra imagen en el endpoint del servidor
     *              gs://instadam-76807.appspot.com/imagenes/nombreImagen
     * @param imageBitmap La imagen en un mapa de Bit necesaria para subir a el servidor.
     * @param subirImagenUsuarioListener Interfaz que necesitamos implementar para tratar de manera sincrona
     *                                  la subida de imagen, con implementar la Interfaz, cuando se llama al metodo
     *                                   addOnSuccessListener es cuando llamamos a los metodos(porque será cuando la imagen se a subido al 100%)
     *                                   
     */
    @SuppressLint("RestrictedApi")
    public static void subirImagen(Context context, String imagenNombre, Bitmap imageBitmap, SubirImagenUsuarioListener subirImagenUsuarioListener){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imagenNombre);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            //Log.d(TAG, "Upload is " + progress + "% done");
            Toast.makeText(context, "Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(exception -> {
            Log.e(TAG, "uploadImage: Failed to upload image", exception);
            subirImagenUsuarioListener.imagenFalloSubida();
        }).addOnSuccessListener(taskSnapshot -> {

            Log.d(TAG, "uploadImage: Image uploaded successfully");
            try {
                subirImagenUsuarioListener.imagenSubida();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * Este metodo es utilizado para descargar imagenes desde firestorage(de 1 en 1), en este metodo la pasamos por parametro la interfaz que hemos definido
     * para que cuando queramos implementar este metodo, tenga que implementarse los metodos de la interfaz, así aunque se descarge de manera asincrona, los datos
     * no se cargaran hasta que este descargada completamente.
     */
    public static void descargarImagen(Context context, String imagenNombre, DescargaImagenUsuarioListener descargaImagenUsuarioListener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imagenNombre);
        final long BYTES = 10*(1024 * 1024);
        storageRef.getBytes(BYTES).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            descargaImagenUsuarioListener.imagenDescargada(bitmap);
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "Error al descargar la imagen de perfil", Toast.LENGTH_SHORT).show();
            descargaImagenUsuarioListener.imagenDescargada(null);
        });
    }
}
