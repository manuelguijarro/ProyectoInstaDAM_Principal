package com.example.instadamfinal.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instadamfinal.R;
import com.example.instadamfinal.activities.MainActivity;
import com.example.instadamfinal.adapters.ImagenAdapter;
import com.example.instadamfinal.adapters.PublicacionAdapter;
import com.example.instadamfinal.controllers.FirebaseManager;
import com.example.instadamfinal.models.Publicacion;
import com.example.instadamfinal.models.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView rvListPublicaciones;
    private PublicacionAdapter publicacionAdapter;
    private List<Publicacion> listPublicacion;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        rvListPublicaciones = view.findViewById(R.id.lista_publicaciones);
        rvListPublicaciones.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuariosDBRef = db.collection("usuarios_db").document("usuario_" + MainActivity.idUnicoStatic);




        usuariosDBRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                if (usuario != null) {

                    listPublicacion = usuario.getPublicaciones();

                    publicacionAdapter = new PublicacionAdapter(listPublicacion);
                    rvListPublicaciones.setAdapter(publicacionAdapter);
                }
            } else {
                Toast.makeText(getContext(), "El documento no existe", Toast.LENGTH_SHORT).show();
            }
        });








        return view;
    }
}