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

public class FireStorageController {


    @SuppressLint("RestrictedApi")
    public static void subirImagen(Context context, String imagenNombre, Bitmap imageBitmap, SubirImagenUsuarioListener subirImagenUsuarioListener){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");
        StorageReference storageRef = storage.getReference().child("imagenes").child(imagenNombre);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //Log.d(TAG, "Upload is " + progress + "% done");
                Toast.makeText(context, "Upload is " + progress + "% done", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "uploadImage: Failed to upload image", exception);
            subirImagenUsuarioListener.imagenFalloSubida();
        }).addOnSuccessListener(taskSnapshot -> {

            Log.d(TAG, "uploadImage: Image uploaded successfully");
            subirImagenUsuarioListener.imagenSubida();
        });
    }







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
