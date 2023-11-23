package Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;

import Negocio.IService;
import Negocio.Service;

public class IUlogin extends AppCompatActivity {
    EditText textUsuario, textPassword;
    TextView linkRegistro;
    IService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        service = Service.getService();

        textUsuario = findViewById(R.id.editTextUsuario);
        textPassword = findViewById(R.id.editTextPassword);
        linkRegistro = findViewById(R.id.textViewRegistro);

    }

    public void clickRegister(View view)
    {
        Intent intent = new Intent(IUlogin.this, IUregistro.class);
        startActivity(intent);
    }

    public void clickLogin(View view)
    {
        Intent intent = new Intent(IUlogin.this, PantallaDeCargaLogin.class);
        intent.putExtra("usuario",textUsuario.getText().toString());
        intent.putExtra("contraseña",textPassword.getText().toString());
        startActivity(intent);
        finish();

       /* Usuario usuario = service.getUsuarioByEmail(textUsuario.getText().toString());
        if(usuario != null && service.passwordMatch(usuario.getContraseña(), textPassword.getText().toString()) )
        {
            guardarToken();

            //Actualizar usuario actual y hacer la transicion
            Intent intent = new Intent(IUlogin.this, IUsugerencias.class);
            startActivity(intent);
            finish();
        }*/
    }

    private void guardarToken()
    {
        // Obtener un objeto SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);

        // Guardar el token de autenticación
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", textUsuario.getText().toString());
        editor.apply();
        service.setLoggedUser(service.getUsuarioByEmail(textUsuario.getText().toString()));

    }

}
