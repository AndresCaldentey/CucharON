package Presentation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Negocio.IService;
import Negocio.Service;
import Persistencia.SingletonConnection;

public class MapaProductos extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private GoogleMap mMap;
    IService servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        servicio = Service.getService();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        for(Producto p : servicio.getAllProducto())
        {
            System.out.println("Hugo mira aqui: " + p.getNombre());
            LatLng posicion = getCoordinatesFromAddress(this, p.getDireccionRecogida());
            //Marker m = mMap.addMarker(new MarkerOptions().position(posicion).title(p.getNombre()));
            //m.setTag(p.getDireccionRecogida());
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        float zoomLevel = 15.0f; // Puedes ajustar este valor según tus necesidades
        LatLng posicionInicial = new LatLng(39.476802,-0.3468245);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicionInicial, zoomLevel);
        mMap.moveCamera(cameraUpdate);

        for(Producto p : servicio.getAllProducto())
        {
            System.out.println("Hugo mira aqui: " + p.getNombre());
            LatLng posicion = getCoordinatesFromAddress(this, p.getDireccionRecogida());
            //Marker m = mMap.addMarker(new MarkerOptions().position(posicion).title(p.getNombre()));
            //m.setTag(p.getDireccionRecogida());
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

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }



    private void moveToCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastLocation != null) {
                LatLng currentLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 50));
            }
        }
    }

    public LatLng getCoordinatesFromAddress(Context context, String locationName) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);

            if (!addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
        finish(); // Cierra la actividad actual y vuelve a la anterior
    }
}
