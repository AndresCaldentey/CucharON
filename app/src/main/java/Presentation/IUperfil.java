package Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.util.List;

import Negocio.*;

public class IUperfil extends AppCompatActivity {
    LinearLayout platos;
    RecyclerView mis_platos;
    ImageView fotoDPerfil;
    Service service;
    TextView nombrePerfil;
    Usuario loggedUser;
    AdaptadorPlato platosAdapter;
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

        //Inicializar platos
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mis_platos.setLayoutManager(linearLayoutManager);
        platosAdapter = new AdaptadorPlato(IUreserva.class);
        mis_platos.setAdapter(platosAdapter);
        //-----------------------------------------------

        //  ----inicializar pantalla----
        //Bitmap foto = service.stringAImagen(loggedUser.getPerfil);
        //fotoDPerfil.setBitmap(foto);

        nombrePerfil.setText(loggedUser.getNombre());
        generarPlatos();


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
    public void generarPlatos() {
        List<Producto> platos = service.getProductosPubPorUser(loggedUser);
        Context context = getApplicationContext();
        platosAdapter.setPlatos(platos);
        platosAdapter.notifyDataSetChanged();

        }


    @Override
    public void onBackPressed() {
        if(mis_platos.getVisibility() == View.VISIBLE) mis_platos.setVisibility(View.GONE);
        else {super.onBackPressed();}

    }



}
