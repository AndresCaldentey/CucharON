package Presentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;


import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Negocio.IService;
import Negocio.Service;

public class IUbuscar extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    public static String direccion = "";
    IService servicio;
    List<Producto> listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        servicio = Service.getService();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listaProductos = servicio.getAllProducto();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //Ajustar camara inicial
        float zoomLevel = 15.0f; // Puedes ajustar este valor según tus necesidades
        LatLng posicionInicial = new LatLng(39.476802,-0.3468245);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicionInicial, zoomLevel);
        mMap.moveCamera(cameraUpdate);

        //Agregar marcadores de puntos en los que se han publicado platos
        for(Producto p : listaProductos)
        {
            LatLng posicion = new LatLng(p.getDireccionLatitud(), p.getDireccionLongitud());
            System.out.println("Hugo mira aqui: " + posicion.toString());
            Marker m = mMap.addMarker(new MarkerOptions().position(posicion).title(p.getNombre()));
            m.setTag(p.getIdProducto());
        }

        //Se hace click en marcador
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                List<Producto> lProducts = servicio.getProductosByDireccion(marker.getTag().toString());
                System.out.println("La lista de productos es de tamaño: " + lProducts.size());
                //Falta mostrar la info del producto por pantalla
                return false; // Devuelve 'true' si consumes el evento, 'false' si no.
            }
        });
    }


    public void posteoProductoOnClick(View view) {
        Intent intent = new Intent(IUbuscar.this, IUposteoProducto.class);
        startActivity(intent);
        finish();
    }
    public void sugerenciasOnClick(View view) {
        Intent intent = new Intent(IUbuscar.this, IUsugerencias.class);
        startActivity(intent);
        finish();
    }
    public void perfilOnClick(View view) {
        Intent intent = new Intent(IUbuscar.this, IUperfil.class);
        startActivity(intent);

    }


}