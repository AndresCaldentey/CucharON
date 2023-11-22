package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.*;

public class IUperfil extends AppCompatActivity {
    LinearLayout platos;
    ScrollView mis_platos;

    ImageView fotoDPerfil;
    Service service;
    TextView nombrePerfil;
    Usuario loggedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Recuperar capa de negocio
        service = Service.getService();

        //Inicializado de variables
        platos = findViewById(R.id.platos);
        mis_platos = findViewById(R.id.mis_platos);
        fotoDPerfil = findViewById(R.id.fotoDPerfil);
        nombrePerfil = findViewById(R.id.nombrePerfil);
        loggedUser = service.getLoggedUser();

        //  ----inicializar pantalla----
        //Bitmap foto = service.stringAImagen(loggedUser.getPerfil);
        //fotoDPerfil.setBitmap(foto);

        nombrePerfil.setText(loggedUser.getNombre());


    }
    public void buscarOnClick(View view) {
        Intent intent = new Intent(IUperfil.this, IUbuscar.class);
        startActivity(intent);
        finish();
    }
    public void posteoProductoOnClick(View view) {
        Intent intent = new Intent(IUperfil.this, IUposteoProducto.class);
        startActivity(intent);
        finish();
    }
    public void sugerenciasOnClick(View view) {
        Intent intent = new Intent(IUperfil.this, IUsugerencias.class);
        startActivity(intent);
        finish();
    }
    public void misPlatosOnClick(View view) {
        mis_platos.setVisibility(View.VISIBLE);

    }


}
