package com.example.instadamfinal.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instadamfinal.R;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class PostFragment extends Fragment {
    private static final int SELECT_PHOTO = 100;

    private EditText editTextTextTituloPublicacionInput;
    private EditText editTextTextMultiLineDescripcionPublicacionInput;
    private ImageView imageViewSubirImagenSubirPostInput;
    private Button buttonPublicarPublicacionInput;
    private Button buttonSubirImagenPost;
    private Bitmap imagenGaleriaBitmap;


    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);

        editTextTextTituloPublicacionInput = view.findViewById(R.id.editTextTextTituloPublicacion);

        editTextTextMultiLineDescripcionPublicacionInput = view.findViewById(R.id.editTextTextMultiLineDescripcionPublicacion);

        buttonPublicarPublicacionInput = view.findViewById(R.id.buttonPublicarPublicacion);

        buttonSubirImagenPost = view.findViewById(R.id.buttonSubirImagen);

        imageViewSubirImagenSubirPostInput = view.findViewById(R.id.imageViewSubirImagenSubirPost);

        buttonSubirImagenPost.setOnClickListener(v -> seleccionarImagenDeGaleria());

        buttonPublicarPublicacionInput.setOnClickListener(v -> {

            //Aqui subimos la imagen
            //y actualizamos la lista de publicaciones del usuario



        });
        return view;
    }

    private void seleccionarImagenDeGaleria() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    Uri imagenGaleriaSeleccionada = data.getData();
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(imagenGaleriaSeleccionada);


                    imagenGaleriaBitmap = BitmapFactory.decodeStream(imageStream);

                    imageViewSubirImagenSubirPostInput.setImageBitmap(imagenGaleriaBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}