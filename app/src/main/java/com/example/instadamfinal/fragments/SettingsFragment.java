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
    private EditText editTextTextPasswordInput;
    private Button buttonSubirImagenUsuario;
    private Button buttonActualizarDatosUsuario;

    private Usuario usuarioLogeado;
    private Fragment fragment;
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

    private void cargarRecursosFragmento(View view) {
        //TextViews
        textViewNombreUsuario = view.findViewById(R.id.textViewNombreUsuario);
        textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        textViewMensajeAlerta = view.findViewById(R.id.textViewMensajeAlertaSettings);
        //Inputs
        editTextTextNombreUsuarioInput = view.findViewById(R.id.editTextTextTituloPublicacion);
        editTextTextEmailUsuarioInput = view.findViewById(R.id.editTextTextEmailUsuario);
        editTextTextPasswordInput = view.findViewById(R.id.editTextTextPassword2);
        //Imagenes
        imageViewSubirImagenActualizarInput = view.findViewById(R.id.imageViewSubirImagenSubirPost);
        imageViewPerfilUsuario = view.findViewById(R.id.imageViewPerfilUsuario);
        //Botones
        buttonSubirImagenUsuario = view.findViewById(R.id.buttonSubirImagen);
        buttonActualizarDatosUsuario = view.findViewById(R.id.buttonPublicarPublicacion);
    }

    private void cargarEventosOnClickBotones() {
        buttonSubirImagenUsuario.setOnClickListener(this::actualizarImagenSubida);
        buttonActualizarDatosUsuario.setOnClickListener(this::actualizarDatosUsuario);
    }



    private void actualizarImagenSubida(View view) {
        seleccionarImagenDeGaleria();
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

                    imageViewSubirImagenActualizarInput.setImageBitmap(imagenGaleriaBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void actualizarDatosUsuario(View view) {

        progressBar2.setVisibility(View.VISIBLE);
        String nombreUsuario = editTextTextNombreUsuarioInput.getText().toString();
        String emailUsuario = editTextTextEmailUsuarioInput.getText().toString();
        String passwordUsuario = editTextTextPasswordInput.getText().toString();

        if (!nombreUsuario.isEmpty() && !emailUsuario.isEmpty()&& !passwordUsuario.isEmpty())
            if (EmailController.comprobarEmail(emailUsuario)&&
                    PasswordController.comprobarPassword(passwordUsuario)){
                //Ahora hemos verificado que los campos no estan vacios, y que el email y contraseña
                //coinciden con los requisitos.
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this.getContext());
                //boolean resultadoExisteEmail = dataBaseHelper.verificarExisteEmailUsuarioHelper(emailUsuario);

                if (/*!resultadoExisteEmail*/true){
                    //Actualizamos los datos de sqlite

                    //Actualizamos los datos de firebase.
                    FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
                    firebaseDataBaseHelper.actualizarDatosUsuarioFirebaseHelper(getContext(), nombreUsuario, emailUsuario,
                            "imagen_perfil" + idUnicoStatic, new UsuarioActualizadoListener() {
                                @Override
                                public void usuarioActualizado() {
                                    //pero antes subimos la imagen.
                                    FireStorageController.subirImagen(getContext(), "imagen_perfil" + idUnicoStatic,
                                            imagenGaleriaBitmap, new SubirImagenUsuarioListener() {
                                                @SuppressLint("RestrictedApi")
                                                @Override
                                                public void imagenSubida() throws InterruptedException {
                                                    Thread.sleep(3000);
                                                    FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
                                                    firebaseDataBaseHelper.cargarDatosUsuarioFirebaseHelper( getContext(), usuario -> {
                                                        if (usuario != null) {
                                                            // Usa el objeto Usuario aquí
                                                            //Realizamos desde aqui los metodos porque nos aseguramso que el usuario se a cargado de la base de datos
                                                            usuarioLogeado = usuario;

                                                            FireStorageController.descargarImagen(getContext(), usuarioLogeado.getUrlImagenPerfil(), bitmap -> {
                                                                if (bitmap != null) {
                                                                    // Aquí es donde debes establecer la imagen en el ImageView

                                                                    progressBar2.setVisibility(View.GONE);
                                                                    imageViewPerfilUsuario.setImageBitmap(bitmap);
                                                                    imageViewPerfilUsuario.setVisibility(View.VISIBLE);

                                                                } else {
                                                                    // Maneja el caso en que la descarga falla o no hay imagen
                                                                    // Podrías mostrar una imagen predeterminada o hacer otra acción aquí
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

                                    //Ahora tendriamos que volver a cargar los datos del usuario.
                                }
                            });




                }else
                //Existe el usuario, no podemos actualizar los datos
                    mostrarMensajeAlerta("El e-mail ya existe, no puedes utilizar un e-mail existente.");
            }else
                mostrarMensajeAlerta("El email o contraseña tiene que contener los requisitos mínimos.");
        else
            mostrarMensajeAlerta("No puede haber ningun campo vacío,rellena todos los campos.");
    }
/*
    private void loadFragment() {
        fragment = new SettingsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }
*/
    private void mostrarMensajeAlerta(String mensaje) {
        textViewMensajeAlerta.setText(mensaje);
    }



    @SuppressLint("RestrictedApi")
    private void cargarDatosUsuarioFirebase(View view) {
        FirebaseDataBaseHelper firebaseDataBaseHelper = new FirebaseDataBaseHelper();
        firebaseDataBaseHelper.cargarDatosUsuarioFirebaseHelper( getContext(), usuario -> {
            if (usuario != null) {
                // Usa el objeto Usuario aquí
                //Realizamos desde aqui los metodos porque nos aseguramso que el usuario se a cargado de la base de datos
                usuarioLogeado = usuario;
                progressBar.setVisibility(View.GONE);
                cargarImagenActualPerfil(view);
                cargarDatosActualPerfil();
            } else
                Log.e(FragmentManager.TAG, "El objeto Usuario es null");
        });
    }

    private void cargarImagenActualPerfil(View view) {

        FireStorageController.descargarImagen(getContext(), usuarioLogeado.getUrlImagenPerfil(), bitmap -> {
            if (bitmap != null) {
                // Aquí es donde debes establecer la imagen en el ImageView
                imageViewPerfilUsuario = view.findViewById(R.id.imageViewPerfilUsuario);

                    imageViewPerfilUsuario.setImageBitmap(bitmap);
                    imageViewPerfilUsuario.setVisibility(View.VISIBLE);

            } else {
                // Maneja el caso en que la descarga falla o no hay imagen
                // Podrías mostrar una imagen predeterminada o hacer otra acción aquí
            }
        });
    }
    private void cargarDatosActualPerfil() {
        textViewNombreUsuario.setText(usuarioLogeado.getUserName());
        textViewEmailUsuario.setText(usuarioLogeado.getEmail());
    }
}
