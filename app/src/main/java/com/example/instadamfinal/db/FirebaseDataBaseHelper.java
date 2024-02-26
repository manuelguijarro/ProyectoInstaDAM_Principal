package com.example.instadamfinal.db;

import static androidx.fragment.app.FragmentManager.TAG;

import static com.example.instadamfinal.activities.MainActivity.idUnicoStatic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.instadamfinal.listeners.UsuarioActualizadoListener;
import com.example.instadamfinal.listeners.UsuarioListener;
import com.example.instadamfinal.models.Publicacion;
import com.example.instadamfinal.models.Usuario;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FirebaseDataBaseHelper {


    @SuppressLint("RestrictedApi")
    public void crearNuevoUsuarioFirebaseHelper(String uniqueID,String nombreUsuario, String emailUsuario){
        //Aqui crear una clase independiente para utilizar todo de firebase.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Publicaciones iniciales/ejemplos
        Publicacion publicacionEjemplo = new Publicacion(0,"default_user.jpg");
        Publicacion publicacionEjemplo2 = new Publicacion(50,"vacaciones_2023.jpg");
        List<Publicacion> publicaciones = new LinkedList<>();
        publicaciones.add(publicacionEjemplo);
        publicaciones.add(publicacionEjemplo2);
                /*
                De esta manera, Firebase adapta el objeto Usuario, que en su interior tiene la listas de publicaciones
                .Es la manera optima y mas recomendada para subir los datos que ofrece firebase.
                NO RECOMIENDA EL USO DE SET Y MAPS
                 */

        Usuario usuarioModelo = new Usuario(uniqueID,nombreUsuario,emailUsuario,"default_user.jpg",
                new Timestamp(new Date()),publicaciones);

                /*
                Aqui añadiriamos un nuevo documento a la base de datos usuarios db, con los datos del usuario
                que hemos añadido a la base de datos local, porque con este usuario de firebase administraremos
                las publicaciones.

                 */
        db.collection("usuarios_db").document("usuario_"+uniqueID)
                .set(usuarioModelo)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    }

    @SuppressLint("RestrictedApi")
    public void cargarDatosUsuarioFirebaseHelper( Context context , UsuarioListener usuarioListener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuariosDBRef = db.collection("usuarios_db").document("usuario_" + idUnicoStatic);

        usuariosDBRef.get().addOnSuccessListener(documentSnapshot -> {
            // Ocultar Toast cuando los datos se hayan descargado(no funciona emulador)
            if (documentSnapshot.exists()) {
                Toast.makeText(context, "Datos cargados", Toast.LENGTH_SHORT).show();
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                usuarioListener.onUsuarioListener(usuario);
            }else
                Log.e(FragmentManager.TAG, "El documento no existe");
        });
    }

    public void actualizarDatosUsuarioFirebaseHelper(Context context, String nombreUsuario, String emailUsuario,
                                                     String urlImagenPerfil, UsuarioActualizadoListener usuarioActualizadoListener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuariosDBRef = db.collection("usuarios_db").document("usuario_" + idUnicoStatic);

        usuariosDBRef.update(
                "email",emailUsuario,
                "urlImagenPerfil",urlImagenPerfil,
                "userName",nombreUsuario
        ).addOnSuccessListener(unused -> usuarioActualizadoListener.usuarioActualizado());
    }
}
