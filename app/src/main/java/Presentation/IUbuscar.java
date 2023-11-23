package Presentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Negocio.IService;
import Negocio.Service;

public class IUbuscar extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    public static String direccion = "";
    IService servicio;
    LinearLayout layoutPlatos;
    List<Producto> listaProductos;
    List<LatLng> posiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        servicio = Service.getService();

        layoutPlatos = findViewById(R.id.layoutPlatos);
        layoutPlatos.removeAllViews();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Carga las posiciones de posteo de productos
        posiciones = new ArrayList<LatLng>();
        listaProductos = servicio.getAllProducto();
        for(Producto p : listaProductos)
        {
            LatLng nuevaPos = new LatLng(p.getDireccionLatitud(), p.getDireccionLongitud());
            if(!posiciones.contains(nuevaPos)) { posiciones.add(nuevaPos); }
        }

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
        for(LatLng p : posiciones)
        {
            Marker m = mMap.addMarker(new MarkerOptions().position(p));
            m.setTag(p.latitude + "h" + p.longitude); //Le pongo este caracter para luego poder separar latitud de longitud

        }

        //Se hace click en marcador
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Se borran los productos de la seleccion anterior
                layoutPlatos.removeAllViews();

                //Se cargan los productos posteado en la posicion seleccionada
                String[] s = marker.getTag().toString().split("h");
                List<Producto> lProducts = servicio.getProductosByPosicion(Double.parseDouble(s[0]), Double.parseDouble(s[1]));

                //Se crea el layout de informacion de cada producto                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             plato
                for(Producto p : lProducts) { crearInfoPlato(p); }

                return false; // Devuelve 'true' si consumes el evento, 'false' si no.
            }
        });
    }

    private LinearLayout crearInfoPlato(Producto producto)
    {
        LinearLayout panelPlato = new LinearLayout(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0);
        layoutParams.setMarginStart(50);
        layoutParams.setMarginEnd(50);
        panelPlato.setLayoutParams(layoutParams);

        panelPlato.setBackgroundResource(R.drawable.fondoplatomapa);
        panelPlato.setOrientation(LinearLayout.HORIZONTAL);
        panelPlato.setTag(producto.getIdProducto());

        ImageView imagen = crearImagenProducto(producto);   //Crea la imagen
        LinearLayout descripcion = crearDescripcion(producto);  //Crea la descripcion del producto con nombre,horario,precio

        panelPlato.addView(imagen);
        panelPlato.addView(descripcion);

        // Agrega el LinearLayout principal a tu layout principal
        layoutPlatos.addView(panelPlato);

        //Han seleccionado un plato
        panelPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Transicion a informacion precisa del plato
                Intent intent = new Intent(IUbuscar.this,IUreserva.class);
                intent.putExtra("Producto",producto.getIdProducto());
                startActivity(intent);
            }
        });

        return panelPlato;
    }

    private ImageView crearImagenProducto(Producto producto)
    {
        // Crea la imageView del producto
        ImageView imagenProducto = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300, 0);
        layoutParams.gravity = Gravity.CENTER;
        imagenProducto.setLayoutParams(layoutParams);
        //imagenProducto.setImageResource(R.drawable.ic_launcher_background);

        byte[] imageBytes = Base64.decode(producto.getImagen(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imagenProducto.setImageBitmap(decodedImage);

        return imagenProducto;
    }

    private LinearLayout crearDescripcion(Producto producto)
    {
        LinearLayout panelDescripcion = new LinearLayout(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                800,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1);
        panelDescripcion.setLayoutParams(layoutParams);

        panelDescripcion.setOrientation(LinearLayout.VERTICAL);

        //NOMBRE PRODUCTO
        TextView nombre = new TextView(this);
        LinearLayout.LayoutParams layoutParamsNombre = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        nombre.setLayoutParams(layoutParamsNombre);
        nombre.setText(producto.getNombre());
        nombre.setTextSize(20);
        nombre.setTypeface(null, Typeface.BOLD);

        panelDescripcion.addView(nombre);

        //HORARIO
        TextView horario = new TextView(this);
        LinearLayout.LayoutParams layoutParamsHorario = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        horario.setLayoutParams(layoutParamsHorario);
        horario.setText(producto.getHoraRecogida());
        horario.setTextSize(16);

        panelDescripcion.addView(horario);

        //PRECIO
        TextView precio = new TextView(this);
        LinearLayout.LayoutParams layoutParamsPrecio = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1);
        precio.setLayoutParams(layoutParamsPrecio);
        precio.setText(producto.getPrecio()+"");
        precio.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        precio.setTextSize(20);
        precio.setTypeface(null, Typeface.BOLD);

        panelDescripcion.addView(precio);

        return panelDescripcion;
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