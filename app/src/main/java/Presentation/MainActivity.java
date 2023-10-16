package Presentation;

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

import com.example.cucharon.R;
import com.example.cucharon.RegistroActivity;
import com.example.cucharon.Usuario;

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

        // Verificar si existe un token de autenticación
        String token = sharedPreferences.getString("token", "");
        System.out.println("El usuario " + token + " ha iniciado sesion anteriormente");
        if (!token.isEmpty()) {
            // El usuario ha iniciado sesión previamente, puedes permitir el acceso a la aplicación.
            Intent intent = new Intent(MainActivity.this, IUsugerencias.class);
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
                editor.putString("token", textUsuario.getText().toString());
                editor.apply();


                //Actualizar usuario actual y hacer la transicion
                Intent intent = new Intent(MainActivity.this, IUsugerencias.class);
                startActivity(intent);
            }

        });
        hilo.start();

    }

}