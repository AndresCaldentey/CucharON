package Presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.net.URISyntaxException;
import java.sql.Connection;

import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;


public class MainActivity extends AppCompatActivity {

    public static Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Comprueba el token de inicio de sesion
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);

        // Verificar si existe un token de autenticación
        String token = sharedPreferences.getString("token", "");
        System.out.println("El usuario " + token + " ha iniciado sesion anteriormente");
        if (!token.isEmpty()) {
            Thread hilo = new Thread(() -> {
                usuarioActual = new UsuarioRepository(SingletonConnection.getSingletonInstance()).getUserByEmail(token);
            });
            hilo.start();

            Intent intent = new Intent(MainActivity.this, IUsugerencias.class);
            startActivity(intent);
        } else
        {
            //El usuario no ha iniciado sesion previamente y se va a login
            Intent intent = new Intent(MainActivity.this, IUlogin.class);
            startActivity(intent);
        }

    }

    public static void setUsuarioActual(Usuario usuarioActual) {
        MainActivity.usuarioActual = usuarioActual;
    }
}