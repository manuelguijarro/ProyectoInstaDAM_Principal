package com.example.instadamfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.instadamfinal.R;
import com.example.instadamfinal.fragments.HomeFragment;
import com.example.instadamfinal.fragments.PostFragment;
import com.example.instadamfinal.fragments.PerfilFragment;
import com.example.instadamfinal.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;

    public static String idUnicoStatic;
    public static String urlImagenPostFragment;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //Aqui nos pasamos el intent desde el inicio de sesion, para utilizar el id unico del usuario.
        Intent intent = getIntent();
        String idUnico = intent.getStringExtra("key");
        idUnicoStatic = idUnico;


        loadFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

       bottomNavigationView.setOnItemSelectedListener(this::cambioFragmento);
    }


    /**
     *
     * Esta funcion la utilizamos para elegir el fragmento que vamos a utilizar mediante click en el menu inferior.
     */
    private boolean cambioFragmento(MenuItem item) {
        int idItemn = item.getItemId();
        if (idItemn == R.id.menu_home){
            fragment = new HomeFragment();
        }
        if(idItemn == R.id.menu_seach){
            fragment = new PerfilFragment();
        }
        if (idItemn == R.id.menu_add_post){
            fragment = new PostFragment();
        }
        if (idItemn == R.id.menu_settings){
            fragment = new SettingsFragment();
        }
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment)
                    .commit();
        }
        return false;
    }

    /**
     * Para que cuando iniciemos el Main activity el primer fragmento que salga sea el Home fragment
     */
    private void loadFragment() {
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }

    /**
     *
     * Esta funcion es necesaria para inflar el menu de opciones en la vista de la app.
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }


}