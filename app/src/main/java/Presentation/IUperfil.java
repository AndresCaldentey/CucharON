package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;

public class IUperfil extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
    public void buscarOnClick(View view) {
        Intent intent = new Intent(IUperfil.this, IUbuscar.class);
        startActivity(intent);
    }
    public void posteoProductoOnClick(View view) {
        Intent intent = new Intent(IUperfil.this, IUposteoProducto.class);
        startActivity(intent);
    }
    public void sugerenciasOnClick(View view) {
        Intent intent = new Intent(IUperfil.this, IUsugerencias.class);
        startActivity(intent);
    }


}
