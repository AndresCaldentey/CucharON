package Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.Service;

public class Perfil extends AppCompatActivity {
    Service servicio;
    TextView nombrePerfil, edadUsuario, valoracion, descripcion;

    Usuario usuarioActual;
    ImageView fotoDPerfil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil2);
        servicio = Service.getService();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        nombrePerfil = findViewById(R.id.nombrePerfil);
        edadUsuario = findViewById(R.id.edadUsuario);
        valoracion = findViewById(R.id.valoracion);
        descripcion = findViewById(R.id.descripcion);
        fotoDPerfil = findViewById(R.id.fotoDPerfil);

         Intent intent = getIntent();
         String userEmail = intent.getStringExtra("usuario");
         usuarioActual = servicio.getUsuarioByEmail(userEmail);

         nombrePerfil.setText(usuarioActual.getNombre());
         //valoracion.setText(usuarioActual.getValoracion().toString());
         //descripcion.setText(usuarioActual.getDescripcion());
         if(usuarioActual.getFoto() != null) {
             fotoDPerfil.setImageBitmap(servicio.pasarStringAImagen(usuarioActual.getFoto())); }


    }

    public void cerrar(View view) {
        finish();
    }

    public void modificar(View view) {
        Intent intent = new Intent(Perfil.this, IUEditPerfil.class);
        intent.putExtra("usuario", usuarioActual.getEmail());
        startActivity(intent);
        finish();
    }
}
