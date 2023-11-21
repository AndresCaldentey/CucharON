package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;

public class IUperfil extends AppCompatActivity {
    LinearLayout platos;
    ScrollView mis_platos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        platos = findViewById(R.id.platos);
        mis_platos = findViewById(R.id.mis_platos);

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
