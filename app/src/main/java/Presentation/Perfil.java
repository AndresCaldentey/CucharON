package Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.util.Date;
import java.util.List;

import Negocio.Service;
import Presentation.Adapters.AdapterOpinion;
import Presentation.Adapters.SliderPlatosEnVenta;
import Negocio.*;

public class Perfil extends AppCompatActivity implements MotionLayout.TransitionListener{
    Service servicio;
    TextView nombrePerfil, edadUsuario, valoracion, descripcion;

    Usuario usuarioActual;
    ImageView fotoDPerfil;

    ViewPager2 mis_platos;

    MotionLayout escenaPrincipal, desplegableDetalles;
    ImageView editarB;
    Button cerrarSesB;
    RecyclerView listaValoraciones;

    CardView noHayOpinion;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil2);
        servicio = Service.getService();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("usuario");
        usuarioActual = servicio.getUsuarioByEmail(userEmail);
        nombrePerfil = findViewById(R.id.nombrePerfil);
        edadUsuario = findViewById(R.id.edadUsuario);
        valoracion = findViewById(R.id.valoracion);
        descripcion = findViewById(R.id.descripcion);
        fotoDPerfil = findViewById(R.id.fotoDPerfil);
        mis_platos = findViewById(R.id.mis_platos);
        cerrarSesB = findViewById(R.id.cerrarSesB);
        noHayOpinion = findViewById(R.id.noHayOpinion);
        escenaPrincipal = findViewById(R.id.escenaPrincipal);
        desplegableDetalles = findViewById(R.id.desplegableDetalles);
        listaValoraciones = findViewById(R.id.listaValoraciones);
        LinearLayoutManager linlayMan = new LinearLayoutManager(this);
        linlayMan.setOrientation(LinearLayoutManager.HORIZONTAL);
        listaValoraciones.setLayoutManager(linlayMan);

        AdapterOpinion.OnClickListener logicaOpinion = new AdapterOpinion.OnClickListener() {
            @Override
            public void click(Usuario usuario) {
                servicio.pulsarPerfil(Perfil.this, usuario);
            }
        };

        List<Producto> productosValorados = servicio.getProductosValorados(usuarioActual);

        if(productosValorados.size() > 0) { noHayOpinion.setVisibility(View.GONE); }

        AdapterOpinion adapterOpinion = new AdapterOpinion(productosValorados, logicaOpinion);
        listaValoraciones.setAdapter(adapterOpinion);



        escenaPrincipal.setTransitionListener(this);
        desplegableDetalles.setTransitionListener(this);

        editarB = findViewById(R.id.editarB);
        if(!userEmail.equals(servicio.getLoggedUser().getEmail())) {
            editarB.setVisibility(View.GONE);
            cerrarSesB.setVisibility(View.GONE);
        }

        List<Producto> productos = servicio.getProductosSinVenderPorUser(usuarioActual);
        mis_platos.setAdapter(new SliderPlatosEnVenta(productos));

         nombrePerfil.setText(usuarioActual.getNombre());
         edadUsuario.setText(obtenerEdad(usuarioActual.getEdad())+" años");
         descripcion.setText(usuarioActual.getBiografia());
         valoracion.setText(servicio.valoracionAString(usuarioActual.getValoracion()));
         if(usuarioActual.getFoto() != null) {
             fotoDPerfil.setImageBitmap(servicio.pasarStringAImagen(usuarioActual.getFoto())); }

         else { fotoDPerfil.setImageResource(R.drawable.martina); }
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

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        updateNestedMotionLayout(motionLayout);
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        updateNestedMotionLayout(motionLayout);
    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        updateNestedMotionLayout(motionLayout);
        if(currentId == R.id.end) {

        }
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
        updateNestedMotionLayout(motionLayout);

    }

    private void updateNestedMotionLayout(MotionLayout motionLayout) {
        if(motionLayout != null) {
            if (motionLayout.getId() == R.id.escenaPrincipal) {
                desplegableDetalles.setProgress(motionLayout.getProgress());

            }
        }
    }


    public String obtenerEdad(Date date){
        int añoActual = new Date().getYear();
        int añoNacimiento = date.getYear();
        int edad = añoActual-añoNacimiento;
        return ""+edad;
    }

    public void cerrarSesion(View view){
        //Logica para cerrar sesión
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);

        // Guardar el token de autenticación
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", null);
        editor.apply();
        servicio.clearLoggedUser();


        Intent intent = new Intent(Perfil.this, IUlogin.class);
        startActivity(intent);
        finish();
    }
}
