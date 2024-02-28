package com.example.instadamfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.DBController;
import com.example.instadamfinal.controllers.EmailController;
import com.example.instadamfinal.controllers.PasswordController;

/**
 * Clase que extiende de Activity, esta clase es la encargada de mostrar la vista asociada a el inicio de sesion
 * y realizar todo el proceso de inicio de sesion de el usuario.
 */

public class SignIn extends AppCompatActivity {
    private EditText editTextEmailUsuario;
    private EditText editTextPasswordUsuario;
    private Button botonEnviar;
    private Button botonCargarRegistroUsuario;
    private TextView textViewMensajeAlertaRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        cargarRecursosVista();
        cargarEventosOnClickBotones();
    }

    /**
     * Funcion para cargar los recuros del xml a nuestros objetos de java, una vez cargados
     * ya podemos empezar a utilizar los objetos.
     */
    private void cargarRecursosVista() {
        //inputs
        editTextEmailUsuario = findViewById(R.id.editTextEmailAddress);
        editTextPasswordUsuario = findViewById(R.id.editTextPassword);
        //botones
        botonEnviar = findViewById(R.id.sendButton);
        botonCargarRegistroUsuario = findViewById(R.id.signUpButton);
        //texto
        textViewMensajeAlertaRegistro = findViewById(R.id.messageAlert);
    }
    /**
     * Funcion para cargar los eventos de botones  para que el usuario pueda interactuar con ellos.
     * tanto como para enviar el formulario como para cargar el activityRegistro
     */
    private void cargarEventosOnClickBotones() {
        botonEnviar.setOnClickListener(this::enviarFormulario);
        botonCargarRegistroUsuario.setOnClickListener(this::cargarActivityRegistro);
    }

    /**
     * Con esta función recogemos los datos del formulario de contacto y verificamos que los input sean correctos(mediante la clase de apoyo
     * EmailController y PasswordController. una vez verificado que el e-mail y contraseña cumple con los requisitos obligatorios, procedemos
     * a logeara nuestro usuario.
     */
    private void enviarFormulario(View v) {
        String emailUsuario = editTextEmailUsuario.getText().toString();
        String passwordUsuario = editTextPasswordUsuario.getText().toString();

        if (EmailController.comprobarEmail(emailUsuario) &&
                PasswordController.comprobarPassword(passwordUsuario)) {
            logearUsuario(emailUsuario, passwordUsuario);
        } else {
            mostrarMensajeAlerta("Datos incorrectos");
        }
    }
    /**
     *Con esta funcion, iniciamos el proceso de logeo de usuario, para ello utilizaremos de ayuda la Clase DBController, que la utilizamos
     * como clase auxiliar para poder encapsular correctamente los metodos y dejar el codigo mejor estructurado.
     */
    private void logearUsuario(String emailUsuario, String passwordUsuario) {
        DBController dbController = new DBController();
        String idUnico = dbController.logearUsuarioController(this.getBaseContext(), emailUsuario, passwordUsuario);

        //Si el idUnico no esta vacío quiere decir que hemos encontrado al usuario.
        if (!idUnico.isEmpty()) {
            mostrarMensajeAlerta("Bienvenido a InstaDAM " + emailUsuario);
            cargarActivityMain(idUnico);
        } else {
            mostrarMensajeAlerta("Error en inicio de sesion, registra un nuevo usuario o edita los inputs");
        }
    }

    /**
     * Con esta función mostramos un mensaje de alerta en la pantalla de Inicio de sesion, el texto que se introduce dependerá del resultado de nuestra
     *  operacion de envioFormulario/login.
     * @param mensaje es el mensaje que recibe como parametro y muestra en la pantalla.
     */
    private void mostrarMensajeAlerta(String mensaje) {
        textViewMensajeAlertaRegistro.setText(mensaje);
    }
    /**
     * Con esta función cargamos el Activity registro(se activa cuando el usuario llama al metodo haciendo click en el boton "botonCargarRegistroUsuario"
     * y le hacemos que tarde 1seg para darle un efecto de carga.
     */
    public void cargarActivityRegistro(View view) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
    /**
     * Con esta función cargamos el Activity Main(se activa cuando el usuario se logea correctamente en nuestra aplicacion).
     * y le hacemos que tarde 1seg para darle un efecto de carga.
     * Recibimos por parametro el idUnico obtenido al logear al usuario, y lo pasamos mediante el Intent a la actividad principal.
     */
    public void cargarActivityMain(String idUnico) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("key", idUnico);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
