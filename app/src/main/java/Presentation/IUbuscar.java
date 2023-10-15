package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;

public class IUbuscar extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugerencias);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }


    public void posteoProductoOnClick(View view) {
        Intent intent = new Intent(IUbuscar.this, IUposteoProducto.class);
        startActivity(intent);
    }
    public void sugerenciasOnClick(View view) {
        Intent intent = new Intent(IUbuscar.this, IUsugerencias.class);
        startActivity(intent);
    }
    public void perfilOnClick(View view) {
        Intent intent = new Intent(IUbuscar.this, IUperfil.class);
        startActivity(intent);
    }


}