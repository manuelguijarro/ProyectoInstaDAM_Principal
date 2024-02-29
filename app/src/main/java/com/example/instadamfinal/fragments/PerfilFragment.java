package com.example.instadamfinal.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadamfinal.R;
import com.example.instadamfinal.activities.MainActivity;
import com.example.instadamfinal.adapters.ImagenAdapter;
import com.example.instadamfinal.controllers.FireStorageController;
import com.example.instadamfinal.controllers.FirebaseManager;
import com.example.instadamfinal.listeners.DescargaImagenUsuarioListener;
import com.example.instadamfinal.models.Publicacion;
import com.example.instadamfinal.models.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Este fragmento carga los datos de perfil de usuario, tanto como las imagenes que tiene subida a su almacenamiento en la nube, como el nombre de usuario y imagen de perfil
 */
public class PerfilFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView textViewNombreUsuarioPerfilText;
    private ProgressBar progressBarPerfil;
    private ProgressBar progressBarImagenPerfil;
    private ImageView imageViewPerfil;

    public PerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        textViewNombreUsuarioPerfilText = view.findViewById(R.id.textViewNombreUsuarioPerfil);
        progressBarPerfil = view.findViewById(R.id.progressBar2);
        progressBarImagenPerfil = view.findViewById(R.id.progressBar7);
        imageViewPerfil = view.findViewById(R.id.imageViewPerfilUsuario);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuariosDBRef = db.collection("usuarios_db").document("usuario_" + MainActivity.idUnicoStatic);


        /*
        Aqui es donde obtenemos el usuario de la base de datos, y creamos una Lista con las url de usuario, para luego añadirlas al recyclerView.
         */



        usuariosDBRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                if (usuario != null) {
                    textViewNombreUsuarioPerfilText.setText(usuario.getUserName());
                    List<String> imageUrls = new ArrayList<>();
                    for (Publicacion publicacion : usuario.getPublicaciones()) {
                        imageUrls.add(publicacion.getUrlImagenPublicacion());
                    }

                    FireStorageController.descargarImagen(getContext(), usuario.getUrlImagenPerfil(), bitmap -> {
                        progressBarImagenPerfil.setVisibility(View.GONE);
                        imageViewPerfil.setImageBitmap(bitmap);
                    });
                    FirebaseManager.downloadImages(getContext(), imageUrls, bitmaps -> {
                        ImagenAdapter imagenAdapter = new ImagenAdapter(bitmaps);
                        progressBarPerfil.setVisibility(View.GONE);
                        recyclerView.setAdapter(imagenAdapter);
                    });
                }
            } else {
                Toast.makeText(getContext(), "El documento no existe", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}