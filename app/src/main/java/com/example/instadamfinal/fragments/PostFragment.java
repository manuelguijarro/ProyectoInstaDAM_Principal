package com.example.instadamfinal.fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.instadamfinal.activities.MainActivity.idUnicoStatic;
import static com.example.instadamfinal.activities.MainActivity.urlImagenPostFragment;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.FireStorageController;
import com.example.instadamfinal.db.FirebaseDataBaseHelper;
import com.example.instadamfinal.listeners.SubirImagenUsuarioListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Fragmento que se encarga de subir nuevas publicaciones a nuestra App, las subira tanto a la base de datos firebase, como la imagen
 * al almacenamiento en la nube Firestorage.
 */
public class PostFragment extends Fragment {
    private static final int SELECT_PHOTO = 100;

    private EditText editTextTextTituloPublicacionInput;
    private EditText editTextTextMultiLineDescripcionPublicacionInput;
    private ImageView imageViewSubirImagenSubirPostInput;
    private Button buttonPublicarPublicacionInput;
    private Button buttonSubirImagenPost;
    private Bitmap imagenGaleriaBitmap;
    private ProgressBar progressBarPost;


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

        imagenGaleriaBitmap = null;

        cargarRecursosVista(view);

        cargarEventosOnClickBotones();

        return view;
    }
    /**
     * Funcion para cargar los recuros del xml a nuestros objetos de java, una vez cargados
     * ya podemos empezar a utilizar los objetos.
     */
    private void cargarRecursosVista(View view) {
        //input
        editTextTextTituloPublicacionInput = view.findViewById(R.id.editTextTextTituloPublicacion);
        editTextTextMultiLineDescripcionPublicacionInput = view.findViewById(R.id.editTextTextMultiLineDescripcionPublicacion);
        //Imagenes
        imageViewSubirImagenSubirPostInput = view.findViewById(R.id.imageViewSubirImagenSubirPost);
        //Botones
        buttonPublicarPublicacionInput = view.findViewById(R.id.buttonPublicarPublicacion);
        buttonSubirImagenPost = view.findViewById(R.id.buttonSubirImagen);
        //ProgressBar
        progressBarPost = view.findViewById(R.id.progressBar);
    }

    /**
     * Funcion para cargar los eventos de botones  para que el usuario pueda interactuar con ellos.
     * tanto como para enviar seleccionar la imagen de la galeria como para publicar la publicacion a firebase y firestorage.
     */
    private void cargarEventosOnClickBotones() {
        buttonSubirImagenPost.setOnClickListener(v -> seleccionarImagenDeGaleria());

        buttonPublicarPublicacionInput.setOnClickListener(v -> publicarPostUsuario());
    }


    /**
     * Funcion que utilizamos para recoger los datos del formulario para posteriormente proceder a subir la imagen al servidor de firestorage y una vez
     * subida añadimos los datos de la nueva publicacion a nuestro usuario en firebase.
     */
    private void publicarPostUsuario(){
        String tituloPublicacion = editTextTextTituloPublicacionInput.getText().toString();
        String descripcionPublicacion = editTextTextMultiLineDescripcionPublicacionInput.getText().toString();

        //Con este if comprobamos que el usuario haya introducido todos los datos necesarios.
        if (imagenGaleriaBitmap !=  null && !tituloPublicacion.isEmpty() && !descripcionPublicacion.isEmpty()){

            urlImagenPostFragment = idUnicoStatic.replace("@","_")+tituloPublicacion;

            progressBarPost.setVisibility(View.VISIBLE);
            try {
                FireStorageController.subirImagen(getContext(), urlImagenPostFragment, imagenGaleriaBitmap, new SubirImagenUsuarioListener() {
                    @Override
                    public void imagenSubida() {

                        FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
                        firebaseDataBaseHelper.aniadirNuevaPublicacionFirebaseHelper(getContext(),tituloPublicacion,descripcionPublicacion);
                        progressBarPost.setVisibility(View.GONE);
                    }

                    @Override
                    public void imagenFalloSubida() {

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Este metodo es para seleccionar una imagen desde la galeria del movil, para luego subirla a firestorage.
     */
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