package com.example.instadamfinal.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Esta clase es utilizada de apoyo para descargar multiples imagenes desde firestorage,
 * nosotros le pasariamos una lista con las url(end point de nuestra ruta de firestorage)
 * y se descargarian una a una mediante un bucle, para luego añadirlas a una lista de bitmaps
 * y poder setearlas correctamente.
 */
public class FirebaseManager {


    private static final String TAG = "FirebaseManager";


    public interface OnImagesDownloadListener {
        void onImagesDownloaded(List<Bitmap> bitmaps);
    }


    /**
     *
     *Metodo que utilizamos de manera estatica para descargar las imagenes de nuestra aplicacion, este metodo es para descargar una lista de url, para descargar
     * de 1 en 1, tenemos otro metodo.
     */
    public static void downloadImages(Context context, List<String> imageUrls, OnImagesDownloadListener listener) {
        List<Bitmap> bitmaps = new ArrayList<>();

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://instadam-76807.appspot.com");

        for (String imageUrl : imageUrls) {
            StorageReference storageRef = storage.getReference().child("imagenes").child(imageUrl);
            final long BYTES = 10 * (1024 * 1024);

            storageRef.getBytes(BYTES).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bitmaps.add(bitmap);

                // Verificar si hemos descargado todas las imágenes
                if (bitmaps.size() == imageUrls.size()) {
                    listener.onImagesDownloaded(bitmaps);
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(context, "Error al descargar una imagen", Toast.LENGTH_SHORT).show();

                // Si falla la descarga de una imagen, simplemente omitimos esa imagen
                // Verificar si hemos descargado todas las imágenes
                if (bitmaps.size() == imageUrls.size()) {
                    listener.onImagesDownloaded(bitmaps);
                }
            });
        }
    }
}