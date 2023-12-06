package Presentation;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import Negocio.IService;
import Negocio.Service;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class IUbuscar extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 123;

    private IService servicio;
    private LinearLayout layoutPlatos;
    private HorizontalScrollView scrollPlatos;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            // Ya se tienen los permisos
            // Puedes iniciar la lógica de ubicación aquí
        } else {
            // Solicitar permisos
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        }
        servicio = Service.getService();
        scrollPlatos = findViewById(R.id.vistaInfoPlatos);
        layoutPlatos = findViewById(R.id.layoutPlatos);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        layoutPlatos.removeAllViews();

        if(mapFragment != null) { mapFragment.getMapAsync(this); }

        layoutPlatos.removeAllViews();  //Borra las vistas de platos de prueba que hay para ver en el xml


    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
                // Puedes iniciar la lógica de ubicación aquí
            } else {
                // Permiso denegado
                // Puedes informar al usuario o intentar solicitar los permisos nuevamente
            }
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap mMap) {
        //Ajustar camara inicial
        this.mMap = mMap;
        float zoomLevel = 15.0f; // Puedes ajustar este valor según tus necesidades
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                        }
                    });
        }
        //LatLng posicionInicial = new LatLng(39.476802,-0.3468245);
       // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicionInicial, zoomLevel);
       // mMap.moveCamera(cameraUpdate);

        //Carga las posiciones de posteo de productos y agrega los marcadores en el mapa
        List<LatLng> posiciones = new ArrayList<>();
        for(Producto p : servicio.getAllProducto())
        {
            LatLng nuevaPos = new LatLng(p.getDireccionLatitud(), p.getDireccionLongitud());
            if(!posiciones.contains(nuevaPos))
            {
                posiciones.add(nuevaPos);
                Marker m = mMap.addMarker(new MarkerOptions().position(nuevaPos));
                if(m != null) { m.setTag(nuevaPos.latitude + "h" + nuevaPos.longitude); }
            }
        }

        //Se hace click en marcador
        mMap.setOnMarkerClickListener(marker -> {
            layoutPlatos.removeAllViews();  // Se borran los productos de la selección anterior
            scrollPlatos.fullScroll(View.FOCUS_LEFT);   // Se recorre el scroll hasta el inicio

            // Se cargan los productos posteados en la posición seleccionada
            if(marker.getTag() != null)
            {
                String[] s = marker.getTag().toString().split("h");
                List<Producto> lProducts = servicio.getProductosByPosicion(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
                for (Producto p : lProducts) { crearInfoPlato(p); } //Se crea el layout de información de cada producto
            }

            return false; // Devuelve 'true' si consumes el evento, 'false' si no.
        });

    }

    private void crearInfoPlato(Producto producto)
    {
        LinearLayout panelPlato = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        layoutParams.setMarginStart(50);
        layoutParams.setMarginEnd(50);
        panelPlato.setLayoutParams(layoutParams);
        panelPlato.setBackgroundResource(R.drawable.fondoplatomapa);
        panelPlato.setOrientation(LinearLayout.HORIZONTAL);

        //Agrega la imagen y la descripcion deproducto al layout
        panelPlato.addView(crearImagenProducto(producto));
        panelPlato.addView(crearDescripcion(producto));

        layoutPlatos.addView(panelPlato);   // Agrega el LinearLayout principal a tu layout principal

        //Han seleccionado un plato y se hace la transicion a IUreserva
        panelPlato.setOnClickListener(v -> {
            Intent intent = new Intent(IUbuscar.this, IUreserva.class);
            intent.putExtra("Producto", producto.getIdProducto());
            startActivity(intent);
        });
    }

    private ImageView crearImagenProducto(Producto producto)
    {
        // Crea la imageView del producto
        ImageView imagenProducto = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300, 0);
        layoutParams.gravity = Gravity.CENTER;
        imagenProducto.setLayoutParams(layoutParams);

        byte[] imageBytes = Base64.decode(producto.getImagen(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imagenProducto.setImageBitmap(decodedImage);

        return imagenProducto;
    }

    private LinearLayout crearDescripcion(Producto producto)
    {
        LinearLayout panelDescripcion = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        panelDescripcion.setLayoutParams(layoutParams);
        panelDescripcion.setOrientation(LinearLayout.VERTICAL);

        //Crea los textviews del nombre, horario y precio
        TextView nombre = crearTextView(producto.getNombre(), 20, true, View.TEXT_ALIGNMENT_TEXT_START);
        TextView horario = crearTextView(producto.getHoraRecogida(), 16, false, View.TEXT_ALIGNMENT_TEXT_START);
        TextView precio = crearTextView(producto.getPrecio()+"€", 20, true, View.TEXT_ALIGNMENT_TEXT_END);

        //Agrega los textviews del nombre, horario y precio al Layout descripcion
        panelDescripcion.addView(nombre);
        panelDescripcion.addView(horario);
        panelDescripcion.addView(precio);

        return panelDescripcion;
    }

    private TextView crearTextView(String texto, int size, boolean esBold, int textAlgiment)
    {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParamsPrecio = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        textView.setLayoutParams(layoutParamsPrecio);
        textView.setTextAlignment(textAlgiment);
        textView.setText(texto);
        textView.setTextSize(size);
        if(esBold) { textView.setTypeface(null, Typeface.BOLD);}

        return textView;
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
        finish();
    }


}