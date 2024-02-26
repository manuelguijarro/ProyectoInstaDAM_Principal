package com.example.instadamfinal.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadamfinal.R;
import com.example.instadamfinal.activities.MainActivity;
import com.example.instadamfinal.adapters.ImagenAdapter;
import com.example.instadamfinal.controllers.FirebaseManager;
import com.example.instadamfinal.models.Publicacion;
import com.example.instadamfinal.models.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PerfilFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView textViewNombreUsuarioPerfilText;

    public PerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Obtener la referencia al RecyclerView en tu layout
        recyclerView = view.findViewById(R.id.recyclerView);
        textViewNombreUsuarioPerfilText = view.findViewById(R.id.textViewNombreUsuarioPerfil);
        // Crear una instancia de GridLayoutManager con orientación horizontal
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);

        // Configurar el RecyclerView con el GridLayoutManager
        recyclerView.setLayoutManager(layoutManager);

        // Crear una referencia a la colección "usuarios_db" en Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuariosDBRef = db.collection("usuarios_db").document("usuario_" + MainActivity.idUnicoStatic);

        // Obtener los datos del usuario de Firestore
        usuariosDBRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Usuario usuario = documentSnapshot.toObject(Usuario.class);
                    if (usuario != null) {
                        textViewNombreUsuarioPerfilText.setText(usuario.getUserName());
                        List<String> imageUrls = new ArrayList<>();
                        for (Publicacion publicacion : usuario.getPublicaciones()) {
                            imageUrls.add(publicacion.getUrlImagenPublicacion());
                        }

                        // Descargar y cargar las imágenes en el RecyclerView
                        FirebaseManager.downloadImages(getContext(), imageUrls, new FirebaseManager.OnImagesDownloadListener() {
                            @Override
                            public void onImagesDownloaded(List<Bitmap> bitmaps) {
                                ImagenAdapter imagenAdapter = new ImagenAdapter(bitmaps);
                                recyclerView.setAdapter(imagenAdapter);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "El documento no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}