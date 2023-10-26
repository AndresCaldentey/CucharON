package Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class IUlogin extends AppCompatActivity {
    Button btnLogin;
    EditText textUsuario, textPassword;
    TextView linkRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnLogin = findViewById(R.id.btnLogin);
        textUsuario = findViewById(R.id.editTextUsuario);
        textPassword = findViewById(R.id.editTextPassword);
        linkRegistro = findViewById(R.id.textViewRegistro);

    }


    public void clickRegister(View view)
    {
        Intent intent = new Intent(IUlogin.this, IUregistro.class);
        startActivity(intent);
        finish();
    }

    public void clickLogin(View view)
    {
        //Comprobar datos del usuario
        Thread hilo = new Thread(() -> {

            Usuario usuario = new UsuarioRepository(SingletonConnection.getSingletonInstance()).getUserByEmail(textUsuario.getText().toString());
            if(usuario != null && usuario.getContraseña().equals(textPassword.getText().toString()))
            {
                // Obtener un objeto SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);

                // Guardar el token de autenticación
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", textUsuario.getText().toString());
                editor.apply();

                MainActivity.setUsuarioActual(usuario);
                //Actualizar usuario actual y hacer la transicion
                Intent intent = new Intent(IUlogin.this, IUsugerencias.class);
                startActivity(intent);
            }



        });
        hilo.start();

    }
}
