package Presentation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;

import Negocio.IService;
import Negocio.Service;

public class IUModificarPerfil extends AppCompatActivity {
    EditText nombreRegistro, apellidoRegistro, telefonoRegistro, emailRegistro, direccionRegistro, passwordRegistro, passwordRegistro2;
    IService service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        nombreRegistro = findViewById(R.id.nombreRegistro);
        apellidoRegistro = findViewById(R.id.apellidoRegistro);
        telefonoRegistro = findViewById(R.id.telefonoRegistro);
        emailRegistro = findViewById(R.id.emailRegistro);
        direccionRegistro = findViewById(R.id.direccionRegistro);
        passwordRegistro = findViewById(R.id.passwordRegistro);
        passwordRegistro2 = findViewById(R.id.passwordRegistro2);
        service = Service.getService();
    }

    public void onClickRegistrar(View v) {
        String mensaje;
        if(passwordRegistro.getText().toString().equals("")) mensaje = "Seguro quieres modificar los datos?";

    }
}
