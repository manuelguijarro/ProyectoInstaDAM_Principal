package com.example.instadamfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.DBController;
import com.example.instadamfinal.controllers.EmailController;
import com.example.instadamfinal.controllers.PasswordController;

public class SignUp extends AppCompatActivity {

    private EditText editTextNombreUsuario;
    private EditText editTextEmailUsuario;
    private EditText editTextPasswordUsuario;
    private TextView textViewMensajeAlertaRegistro;
    private Button botonEnviar;
    private Button botonVolverInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        cargarRecursosVista();
        cargarEventosOnClickBotones();
    }
    /**
     * Funcion para cargar los recuros del xml a nuestros objetos de java, una vez cargados
     * ya podemos empezar a utilizar los objetos.
     */
    private void cargarRecursosVista() {
        //input
        editTextNombreUsuario = findViewById(R.id.editTextUserName);
        editTextEmailUsuario = findViewById(R.id.editTextEmailAddress);
        editTextPasswordUsuario = findViewById(R.id.editTextPassword);
        //Texto
        textViewMensajeAlertaRegistro = findViewById(R.id.textViewMensajeAlerta);
        //Botones
        botonVolverInicioSesion = findViewById(R.id.signUpButton);
        botonEnviar = findViewById(R.id.sendButton);
    }
    /**
     * Funcion para cargar los eventos de botones  para que el usuario pueda interactuar con ellos.
     * tanto como para enviar el formulario como para cargar el activityInicioSesion
     */
    private void cargarEventosOnClickBotones() {
        botonEnviar.setOnClickListener(this::enviarFormulario);
        botonVolverInicioSesion.setOnClickListener(this::cargarActivityInicioSesion);
    }
    /**
     * Con esta función recogemos los datos del formulario de registro y verificamos que los input sean correctos(mediante la clase de apoyo
     * EmailController y PasswordController. una vez verificado que el e-mail y contraseña cumple con los requisitos obligatorios, procedemos
     * a registrar nuestro usuario.
     */
    private void enviarFormulario(View v) {
        String nombreUsuario = editTextNombreUsuario.getText().toString();
        String emailUsuario = editTextEmailUsuario.getText().toString();
        String passwordUsuario = editTextPasswordUsuario.getText().toString();


        if (!nombreUsuario.isEmpty() &&
                EmailController.comprobarEmail(emailUsuario) &&
                PasswordController.comprobarPassword(passwordUsuario)) {

            registrarNuevoUsuario(nombreUsuario, emailUsuario, passwordUsuario);
            cargarActivityInicioSesion(v);
        } else
            mostrarMensajeAlerta("Datos incorrectos, vuelve a introducir los datos correctamente");
    }

    /**
     *Con esta funcion nos apoyamos para realizar el proceso de registro de nuevo usuario, utilizando la clase DBController.
     * @param nombreUsuario
     * @param emailUsuario
     * @param passwordUsuario
     */
    private void registrarNuevoUsuario(String nombreUsuario, String emailUsuario, String passwordUsuario) {
        DBController dbController = new DBController();
        boolean usuarioCreado = dbController.registrarUsuario(this.getBaseContext(), nombreUsuario, emailUsuario, passwordUsuario);

        if (usuarioCreado)
            mostrarMensajeAlerta("Usuario creado, bienvenido a InstaDAM " + nombreUsuario);
        else
            mostrarMensajeAlerta("Error en la creacion del usuario, el usuario ya existe");
    }
    /**
     * Con esta función mostramos un mensaje de alerta en la pantalla de Registro, el texto que se introduce dependerá del resultado de nuestra
     * operacion de envioFormulario/registro.
     */
    private void mostrarMensajeAlerta(String mensaje) {
        textViewMensajeAlertaRegistro.setText(mensaje);
    }
    /**
     * Con esta función cargamos el Activity inicio sesion.
     */
    public void cargarActivityInicioSesion(View view) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
