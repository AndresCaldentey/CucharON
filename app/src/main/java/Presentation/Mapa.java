package Presentation;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cucharon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    GoogleMap mMap;
    EditText txtlatitud;
    EditText txtLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa2);

        txtlatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng españa = new LatLng(39.476802,-0.3468245);
        mMap.addMarker(new MarkerOptions().position(españa).title("UPV")); // Indica la posicion en la que abres el mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLng(españa)); // Añade una marca en el mapa
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        LatLng puntoSelecionado = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(puntoSelecionado));
        txtlatitud.setText(""+latLng.latitude);
        txtLongitud.setText(""+latLng.longitude);

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        txtlatitud.setText(""+latLng.latitude);
        txtLongitud.setText(""+latLng.longitude);

    }
}
