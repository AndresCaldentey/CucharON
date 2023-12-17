package Presentation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cucharon.Plato_publicado;
import com.example.cucharon.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;


public class Add_punto_encuentro extends Fragment implements OnMapReadyCallback {

    private OnDataPassListener dataPassListener;
    EditText direccionEditText;
    private GoogleMap googleMap;
    ImageView buscarButton;
    Button botonSubirPlato;
    String direccion;
    public Add_punto_encuentro() {
        // Required empty public constructor
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);


        botonSubirPlato = view.findViewById(R.id.botonSubirPlato);

        botonSubirPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarAlertaConDosOpciones();

            }
        });


        // Inicializa el mapa si aún no se ha inicializado
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.mapFragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        direccionEditText = view.findViewById(R.id.direccionEditText);
        buscarButton = view.findViewById(R.id.buscarButton2);

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUbicacion();
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Configura el listener de clic en el mapa
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Muestra la ubicación en el EditText
                direccion = getAddressFromCoordinates(requireContext(), latLng.latitude, latLng.longitude);

                direccionEditText.setText(direccion);

                // Coloca una marca en el mapa
                googleMap.clear(); // Borra todas las marcas anteriores
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                googleMap.addMarker(markerOptions);

                // Mueve la cámara al nuevo punto
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null && googleMap != null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                            direccion = getAddressFromCoordinates(requireContext(), location.getLatitude(), location.getLongitude());
                            direccionEditText.setText(direccion);
                        }
                    });
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_punto_encuentro, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (OnDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnDataPassListener");
        }
    }

    private void buscarUbicacion() {
        String direccion = direccionEditText.getText().toString().trim();

        // Utiliza la API de Geocodificación para obtener las coordenadas de la dirección
        // Esto debe ejecutarse en un hilo separado para evitar bloquear el hilo principal
        // (podrías considerar el uso de AsyncTask o Kotlin Coroutines para operaciones asíncronas)
        // Aquí, se realiza de manera sincrónica solo para simplificar el ejemplo.
        Geocoder geocoder = new Geocoder(requireContext());
        try {
            List<Address> direcciones = geocoder.getFromLocationName(direccion, 1);
            if (!direcciones.isEmpty()) {
                Address ubicacion = direcciones.get(0);
                LatLng latLng = new LatLng(ubicacion.getLatitude(), ubicacion.getLongitude());

                // Coloca una marca en el mapa
                googleMap.clear(); // Borra todas las marcas anteriores
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                googleMap.addMarker(markerOptions);

                // Mueve la cámara a la nueva ubicación
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void sendDireccionToActivity(String data) {
        DataObject dataObject = new DataObject("direccion", data);
        dataPassListener.onDataPass(dataObject);
    }

    private void mostrarAlertaConDosOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¡Estamos apuntos de terminar!");
        builder.setMessage("¿Estás seguro que quieres usar esta direccion?");

        // Configurar botón positivo (Sí)
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en Sí
                // Puedes agregar lógica adicional aquí si es necesario
                dialog.dismiss(); // Cierra la alerta
                sendDireccionToActivity(direccion);
                getParentFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new Plato_publicado()).commit();

            }
        });

        // Configurar botón negativo (No)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en No
                // Puedes agregar lógica adicional aquí si es necesario
                dialog.dismiss(); // Cierra la alerta
            }
        });

        // Mostrar la alerta
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}