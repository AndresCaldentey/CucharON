package Presentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.app.ActivityCompat;


import com.example.cucharon.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private GoogleMap mMap;
    public static String direccion = "";
    public static double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        //Ajustar camara inicial
        float zoomLevel = 15.0f;
        LatLng posicionInicial = new LatLng(39.476802,-0.3468245);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicionInicial, zoomLevel);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();   //Borra antigua seleccion de posicion

        mMap.addMarker(new MarkerOptions().position(latLng));
        direccion = getAddressFromCoordinates(this,latLng.latitude,latLng.longitude);
        latitud = latLng.latitude;
        longitud = latLng.longitude;
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    private String getAddressFromCoordinates(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
        Intent resultIntent = new Intent();
        resultIntent.putExtra("direccion", direccion); // Supongamos que "ubicacionSeleccionada" es el dato que deseas enviar de vuelta
        resultIntent.putExtra("latitud" , latitud);
        resultIntent.putExtra("longitud" , longitud);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
        finish(); // Cierra la actividad actual y vuelve a la anterior
    }

}
