package com.example.cucharon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;


public class MainActivity extends AppCompatActivity {

    Connection connection;
    Button btnLogin;
    EditText textUsuario, textPassword;
    TextView linkRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnLogin = findViewById(R.id.btnLogin);
        textUsuario = findViewById(R.id.editTextUsuario);
        textPassword = findViewById(R.id.editTextPassword);
        linkRegistro = findViewById(R.id.textViewRegistro);

        //Comprueba el token de inicio de sesion
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);

        setContentView(R.layout.login);

        // Verificar si existe un token de autenticación
        String token = sharedPreferences.getString("token", "");

        if (!token.isEmpty()) {
            // El usuario ha iniciado sesión previamente, puedes permitir el acceso a la aplicación.
            Intent intent = new Intent(MainActivity.this, SugerenciasActivity.class);
            startActivity(intent);
        }
    }

    public void clickRegister(View view)
    {
        Toast.makeText(this, "Se ha clickado Registro", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        startActivity(intent);
    }

    public void clickLogin(View view)
    {
        //Comprobar datos del usuario
        Thread hilo = new Thread(() -> {

            Usuario usuario = new UsuarioRepository(SingletonConnection.getSingletonInstance()).getUserByName(textUsuario.getText().toString());
            if(usuario != null && usuario.getContraseña().equals(textPassword.getText().toString()))
            {
                // Obtener un objeto SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);

                // Guardar el token de autenticación
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", "tokenLogin");
                editor.apply();


                //Actualizar usuario actual y hacer la transicion
                //Toast.makeText(this, "El usuario y la contraseña es correcto", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SugerenciasActivity.class);
                startActivity(intent);
            }

        });
        hilo.start();

    }

}