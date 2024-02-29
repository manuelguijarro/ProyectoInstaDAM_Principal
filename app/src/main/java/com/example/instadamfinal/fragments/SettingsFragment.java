package com.example.instadamfinal.fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.instadamfinal.activities.MainActivity.idUnicoStatic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.EmailController;
import com.example.instadamfinal.controllers.FireStorageController;
import com.example.instadamfinal.controllers.PasswordController;
import com.example.instadamfinal.db.DataBaseHelper;
import com.example.instadamfinal.db.FirebaseDataBaseHelper;
import com.example.instadamfinal.listeners.SubirImagenUsuarioListener;
import com.example.instadamfinal.listeners.UsuarioActualizadoListener;
import com.example.instadamfinal.models.Usuario;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class SettingsFragment extends Fragment {
    private ImageView imageViewPerfilUsuario;
    private ImageView imageViewSubirImagenActualizarInput;
    private Bitmap imagenDescargadaPerfil;
    private Bitmap imagenGaleriaBitmap;
    private TextView textViewNombreUsuario;
    private TextView textViewEmailUsuario;
    private TextView textViewMensajeAlerta;
    private EditText editTextTextNombreUsuarioInput;
    private EditText editTextTextEmailUsuarioInput;
    private Button buttonSubirImagenUsuario;
    private Button buttonActualizarDatosUsuario;

    private Usuario usuarioLogeado;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private static final int SELECT_PHOTO = 100;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Toast.makeText(getContext(), "Cargando datos...", Toast.LENGTH_SHORT).show();
        progressBar = view.findViewById(R.id.progressBar3);
        progressBar2 = view.findViewById(R.id.progressBar4);
        cargarDatosUsuarioFirebase(view);

        cargarRecursosFragmento(view);

        cargarEventosOnClickBotones();


        return view;
    }

    /**
     * Funcion para cargar los recuros del xml a nuestros objetos de java, una vez cargados
     * ya podemos empezar a utilizar los objetos.
     */
    private void cargarRecursosFragmento(View view) {
        //TextViews
        textViewNombreUsuario = view.findViewById(R.id.textViewNombreUsuario);
        textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        textViewMensajeAlerta = view.findViewById(R.id.textViewMensajeAlertaSettings);
        //Inputs
        editTextTextNombreUsuarioInput = view.findViewById(R.id.editTextTextTituloPublicacion);
        editTextTextEmailUsuarioInput = view.findViewById(R.id.editTextTextEmailUsuario);

        //Imagenes
        imageViewSubirImagenActualizarInput = view.findViewById(R.id.imageViewSubirImagenSubirPost);
        imageViewPerfilUsuario = view.findViewById(R.id.imageViewPerfilUsuario);
        //Botones
        buttonSubirImagenUsuario = view.findViewById(R.id.buttonSubirImagen);
        buttonActualizarDatosUsuario = view.findViewById(R.id.buttonPublicarPublicacion);
    }
    /**
     * Funcion para cargar los eventos de botones  para que el usuario pueda interactuar con ellos.
     * tanto como para subir la imagen, como para actualizar los datos de usuario.
     */
    private void cargarEventosOnClickBotones() {
        buttonSubirImagenUsuario.setOnClickListener(this::actualizarImagenSubida);
        buttonActualizarDatosUsuario.setOnClickListener(this::actualizarDatosUsuario);
    }



    private void actualizarImagenSubida(View view) {
        seleccionarImagenDeGaleria();
    }

    /**
     * Metodo que utiliza los Intent para obtener una imagen de nuestra galeria.
     */
    private void seleccionarImagenDeGaleria() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    /**
     *
     *Una vez obtenido el Intent, tenemos que obtener el dato y descargarlo mediante un InputStream, y luego añadirlo a el objeto Bitmap para actualizar el perfil despues.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    Uri imagenGaleriaSeleccionada = data.getData();
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(imagenGaleriaSeleccionada);


                    imagenGaleriaBitmap = BitmapFactory.decodeStream(imageStream);

                    imageViewSubirImagenActualizarInput.setImageBitmap(imagenGaleriaBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Este metodo es el necesario para actualizar todos los datos de Perfil de usuario, tanto el nombre e email, como la imagen de perfil.(solo actualiza el usuario de firebase)
     */
    private void actualizarDatosUsuario(View view) {

        progressBar2.setVisibility(View.VISIBLE);
        String nombreUsuario = editTextTextNombreUsuarioInput.getText().toString();
        String emailUsuario = editTextTextEmailUsuarioInput.getText().toString();

        if (!nombreUsuario.isEmpty() && !emailUsuario.isEmpty())
            if (EmailController.comprobarEmail(emailUsuario)){

                    FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
                    firebaseDataBaseHelper.actualizarDatosUsuarioFirebaseHelper(getContext(), nombreUsuario, emailUsuario,
                            "imagen_perfil" + idUnicoStatic, () -> {
                                //pero antes subimos la imagen.
                                FireStorageController.subirImagen(getContext(), "imagen_perfil" + idUnicoStatic,
                                        imagenGaleriaBitmap, new SubirImagenUsuarioListener() {
                                            @SuppressLint("RestrictedApi")
                                            @Override
                                            public void imagenSubida() throws InterruptedException {
                                                Thread.sleep(3000);
                                                FirebaseDataBaseHelper firebaseDataBaseHelper1 = new FirebaseDataBaseHelper();
                                                firebaseDataBaseHelper1.cargarDatosUsuarioFirebaseHelper( getContext(), usuario -> {
                                                    if (usuario != null) {
                                                       usuarioLogeado = usuario;
                                                        FireStorageController.descargarImagen(getContext(), usuarioLogeado.getUrlImagenPerfil(), bitmap -> {
                                                            if (bitmap != null) {

                                                                progressBar2.setVisibility(View.GONE);
                                                                imageViewPerfilUsuario.setImageBitmap(bitmap);
                                                                imageViewPerfilUsuario.setVisibility(View.VISIBLE);

                                                            }
                                                        });
                                                        cargarDatosActualPerfil();
                                                    } else
                                                        Log.e(FragmentManager.TAG, "El objeto Usuario es null");
                                                });
                                            }
                                            @Override
                                            public void imagenFalloSubida() {

                                            }
                                        });
                            });

            }else
                mostrarMensajeAlerta("El email o contraseña tiene que contener los requisitos mínimos.");
        else
            mostrarMensajeAlerta("No puede haber ningun campo vacío,rellena todos los campos.");
    }

    private void mostrarMensajeAlerta(String mensaje) {
        textViewMensajeAlerta.setText(mensaje);
    }

    /**
     * Metodo utilizado para cargar los datos de nuestro usuario de firebase, para luego setear sus datos.
     */

    @SuppressLint("RestrictedApi")
    private void cargarDatosUsuarioFirebase(View view) {
        FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
        firebaseDataBaseHelper.cargarDatosUsuarioFirebaseHelper( getContext(), usuario -> {
            if (usuario != null) {

                usuarioLogeado = usuario;
                progressBar.setVisibility(View.GONE);
                cargarImagenActualPerfil(view);
                cargarDatosActualPerfil();
            } else
                Log.e(FragmentManager.TAG, "El objeto Usuario es null");
        });
    }

    /**
     * Metodo que utiliza la clase controladora FireStorageController y su interfaz para descargar una imagen de perfil del usuario y una vez descargada añadirsela.
     */
    private void cargarImagenActualPerfil(View view) {

        FireStorageController.descargarImagen(getContext(), usuarioLogeado.getUrlImagenPerfil(), bitmap -> {
            if (bitmap != null) {
                // Aquí es donde debes establecer la imagen en el ImageView
                imageViewPerfilUsuario = view.findViewById(R.id.imageViewPerfilUsuario);

                    imageViewPerfilUsuario.setImageBitmap(bitmap);
                    imageViewPerfilUsuario.setVisibility(View.VISIBLE);
            }
        });
    }
    private void cargarDatosActualPerfil() {
        textViewNombreUsuario.setText(usuarioLogeado.getUserName());
        textViewEmailUsuario.setText(usuarioLogeado.getEmail());
    }
}
