package Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentContainerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import Negocio.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class Navegacion extends AppCompatActivity {
    private Service servicio;
    private ChipNavigationBar barraNav;
    private int previousIndex;
    FragmentContainerView mainFragmentContainer;
    private List<Producto> listaProductos;
    CircleImageView imagenPerfil;

    List<Producto> allProductos;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barra_navegacion);
        servicio = Service.getService();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        barraNav = findViewById(R.id.barraNav);
        mainFragmentContainer = findViewById(R.id.mainFragmentContainer);
        imagenPerfil = findViewById(R.id.imagen_perfil);
        Usuario loggedUser = servicio.getLoggedUser();
        if(loggedUser.getFoto() != null) imagenPerfil.setImageBitmap(servicio.pasarStringAImagen(loggedUser.getFoto()));

        listaProductos = servicio.getPrimerosProductos();

        //Hilo para recoger todos los productos de fondo
        Thread hilo = new Thread(() ->
        {
            List<Producto> lproduct = servicio.getProductosSinComprar();
            runOnUiThread(() -> setListaProductos(lproduct));
        });
        hilo.start();


        barraNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if(i == R.id.home) {
                    previousIndex = i;
                    //getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Home()).addToBackStack(null).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Home()).commit();
                    showPerfil();
                }
                if(i == R.id.search) {
                    previousIndex = R.id.search;
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Examinar()).commit();
                    showPerfil();
                }
                if(i == R.id.add) {
                    barraNav.setItemSelected(previousIndex, true);
                    Intent intent = new Intent(Navegacion.this, IUAddPlato.class);
                    startActivity(intent);
                }
                if(i == R.id.cesta) {
                    previousIndex = R.id.cesta;
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Cesta()).commit();
                    showPerfil();
                }

            }

        });
        barraNav.setItemSelected(R.id.home, true);

    }

    public List<Producto> getAllProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> lproductos) {
        listaProductos = lproductos;
        //Falta notificar al frgament si es home o examinar
        if(mainFragmentContainer.getFragment() instanceof Home) {
            Home home = (Home) mainFragmentContainer.getFragment();
            home.setProductos(lproductos);
        }
    }

    public void hidePerfil() {
        imagenPerfil.setVisibility(View.GONE);
    }
    public void showPerfil() {
        if(imagenPerfil.getVisibility() == View.VISIBLE) return;
        imagenPerfil.setVisibility(View.VISIBLE);
    }

    public void clickPerfil(View view) {
        servicio.pulsarPerfil(this, servicio.getLoggedUser());
    }

    @Override
    public void onBackPressed() {
        if(mainFragmentContainer.getFragment() instanceof VerBusqueda) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Examinar()).commit();
            showPerfil();
        }else
        {
            super.onBackPressed();
        }
    }
}
