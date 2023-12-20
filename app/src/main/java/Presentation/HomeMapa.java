package Presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Negocio.IService;
import Negocio.Service;
import Presentation.Adapters.AdaptadorPlatoMapa;

public class HomeMapa extends Fragment implements OnMapReadyCallback {
    private IService servicio;
    private static final double RADIO_TIERRA = 6371000.0;
    int[] arrayImagenesPines = { R.drawable.platico1, R.drawable.platico2, R.drawable.platico3, R.drawable.platico4 };
    private List<Producto> productos;
    private AdaptadorPlatoMapa adaptadorPlatos;
    private ViewPager2 platoMapaViewer;

    public HomeMapa() {}

    public void setProductos(List<Producto> listaProductos) {
        adaptadorPlatos.setProductos(listaProductos);
        adaptadorPlatos.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getParentFragmentManager().findFragmentById(R.id.map);

        //Inicializacion variables programa
        servicio = Service.getService();
        platoMapaViewer = view.findViewById(R.id.platosMapaViewer);

        //Carga las posiciones de posteo de productos y agrega los marcadores en el mapa
        if(getActivity() != null) {
            if(getActivity() instanceof Navegacion) {
                Navegacion activityActual = (Navegacion) getActivity();
                productos = activityActual.getAllProductos();
            }
        }
        adaptadorPlatos = new AdaptadorPlatoMapa(new ArrayList<>(), getActivity());
        platoMapaViewer.setAdapter(adaptadorPlatos);

        // Inicializa el mapa si aún no se ha inicializado
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap mMap) {
        //Carga las posiciones de posteo de productos y agrega los marcadores en el mapa
        if(getActivity() != null) {
            if(getActivity() instanceof Navegacion) {
                Navegacion activityActual = (Navegacion) getActivity();
                productos = activityActual.getAllProductos();
            }
        }

        //Ajustar camara inicial si existen los permisos de ubicacion
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                if (location != null) {
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                }
            });
        }

        List<LatLng> posiciones = new ArrayList<>();
        for(Producto p : productos)
        {
            LatLng nuevaPos = new LatLng(p.getDireccionLatitud(), p.getDireccionLongitud());
            if(!posicionEstaEnLista(posiciones,nuevaPos))
            {
                posiciones.add(nuevaPos);
                Marker m = mMap.addMarker(new MarkerOptions().position(nuevaPos).icon(obtainRandomIcon()));
                if(m != null) { m.setTag(nuevaPos.latitude + "h" + nuevaPos.longitude); }
            }
        }

        //Se hace click en marcador
        mMap.setOnMarkerClickListener(marker -> {
            // Se cargan los productos posteados en la posición seleccionada
            if(marker.getTag() != null)
            {
                String[] s = marker.getTag().toString().split("h");

                List<Producto> productosPunto = new ArrayList<>();
                for(Producto p : productos) {
                    if(p.getDireccionLatitud() == Double.parseDouble(s[0])
                            && p.getDireccionLongitud() == Double.parseDouble(s[1])) {
                        productosPunto.add(p);
                    }
                }

                setProductos(productosPunto);
            }
            return false; // Devuelve 'true' si consumes el evento, 'false' si no.
        });

    }

    private BitmapDescriptor obtainRandomIcon(){
        Random random = new Random();
        int indiceAleatorio = random.nextInt(arrayImagenesPines.length);
        int drawableAleatorio = arrayImagenesPines[indiceAleatorio];
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(drawableAleatorio);
        return icon;
    }


    private boolean posicionEstaEnLista(List<LatLng> listaPosiciones,LatLng nuevaPos){
        double areaUmbral = 10;
        for (LatLng pos:listaPosiciones) {
            if(estanEnLaMismaArea(pos, nuevaPos, areaUmbral)){
                return true;
            }
        }
        return false;
    }

    private double calcularDistancia(LatLng punto1, LatLng punto2) {
        // Convierte las coordenadas de grados a radianes
        double latitud1 = Math.toRadians(punto1.latitude);
        double longitud1 = Math.toRadians(punto1.longitude);
        double latitud2 = Math.toRadians(punto2.latitude);
        double longitud2 = Math.toRadians(punto2.longitude);

        // Calcula las diferencias de latitud y longitud
        double dlat = latitud2 - latitud1;
        double dlon = longitud2 - longitud1;

        // Fórmula haversine
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(latitud1) * Math.cos(latitud2) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en metros
        return RADIO_TIERRA * c;
    }

    private boolean estanEnLaMismaArea(LatLng punto1, LatLng punto2, double areaUmbral) {
        // Calcula la distancia entre los dos puntos
        double distancia = calcularDistancia(punto1, punto2);

        // Comprueba si la distancia es menor o igual al umbral
        return distancia <= areaUmbral;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.map, container, false);
    }
}
