package Presentation;

//import static Presentation.Mapa.REQUEST_CODE_LOCATION_PERMISSION;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

//import com.codeboy.pager2_transformers.Pager2_PopTransformer;
import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.util.ArrayList;
import java.util.List;
import Negocio.CustomFontTextView;
import Negocio.IService;
import Negocio.Service;
import Presentation.Adapters.AdaptadorHome;
import de.hdodenhof.circleimageview.CircleImageView;


public class Home extends Fragment {
    private IService service;
    private TextView nombrePerfil, precioPlato, valoracionPerfil, nombrePublicador;
    private TextView nombrePlato;
    private ViewPager2 platosSliderHome;
    private CircleImageView fotoPerfilPub;
    private ToggleButton btnMapa, btnMasBarato, btnMasCaro, btnVendedorTop;
    private List<Producto> productos;
    private AdaptadorHome adaptadosPlatosHome;
    private List<ToggleButton> botones = new ArrayList<>();
    LinearLayout perfilPubLayout;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 123;

    public Home() {}

    public void setProductos(List<Producto> listaProductos) {
        productos = listaProductos;
        adaptadosPlatosHome.setProductos(listaProductos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Inicializacion variables programa
        service = Service.getService();
        nombrePerfil = view.findViewById(R.id.nombrePerfilHome);
        precioPlato = view.findViewById(R.id.precioLabel);
        valoracionPerfil = view.findViewById(R.id.valoracionPerfil);
        nombrePublicador = view.findViewById(R.id.nombrePublicador);
        nombrePlato = view.findViewById(R.id.nombrePlatoLabel);
        fotoPerfilPub = view.findViewById(R.id.fotoPerfilPub);
        platosSliderHome = view.findViewById(R.id.platosSliderHome);
        perfilPubLayout = view.findViewById(R.id.perfilPubLayout);
        btnMapa = view.findViewById(R.id.mapaB);
        btnMasBarato = view.findViewById(R.id.masBaratoB);
        btnMasCaro = view.findViewById(R.id.masCaroB);
        btnVendedorTop = view.findViewById(R.id.vendedorTopB);

        //Inicialización del Slider
        if(getActivity() != null) {
            if(getActivity() instanceof Navegacion) {
                Navegacion activityActual = (Navegacion) getActivity();
                productos = activityActual.getAllProductos();
            }
        }
        adaptadosPlatosHome = new AdaptadorHome(productos, getActivity());
        platosSliderHome.setAdapter(adaptadosPlatosHome);

        //Codigo para hacer el slider con su animación de hacer los otros platos en chiquitito
        platosSliderHome.setClipToPadding(false);
        platosSliderHome.setClipChildren(false);
        platosSliderHome.setOffscreenPageLimit(3);
        platosSliderHome.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        //compositePageTransformer.addTransformer(new MarginPageTransformer(20));

        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
                page.setScaleX(0.85f + r * 0.15f);
            }
        });

        platosSliderHome.setPageTransformer(compositePageTransformer);
        actualizarPantalla(productos.get(0));
        platosSliderHome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                actualizarPantalla(productos.get(position));
            }
        });

        botones.add(btnMapa);
        botones.add(btnMasBarato);
        botones.add(btnMasCaro);
        botones.add(btnVendedorTop);

        btnMapa.setOnClickListener((view1) -> { if(btnMapa.isChecked()) actualizarBotones(btnMapa); });

        btnMasBarato.setOnClickListener((view1) -> { if(btnMasBarato.isChecked()) actualizarBotones(btnMasBarato); });

        btnMasCaro.setOnClickListener((view1) -> { if(btnMasCaro.isChecked()) actualizarBotones(btnMasCaro); });

        btnVendedorTop.setOnClickListener((view1) -> { if(btnVendedorTop.isChecked()) actualizarBotones(btnVendedorTop); });




        if (ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            // Ya se tienen los permisos
            // Puedes iniciar la lógica de ubicación aquí
        } else {
            // Solicitar permisos
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        }
    }

    @SuppressLint("ResourceAsColor")
    public void actualizarBotones(ToggleButton justCheckedB){
        for(ToggleButton button : botones) {
            if(!button.equals(justCheckedB)) { button.setChecked(false); }
        }
    }

    private void actualizarPantalla(Producto producto) {
        Usuario publicador = producto.getUsuarioPublicador();
        nombrePerfil.setText(service.getLoggedUser().getNombre());
        precioPlato.setText(producto.getPrecio().toString() + " euros");
        //int valoracion = publicador().getValoracion();
        //String valoracionText = String.valueOf('*').repeat(valoracion);
        //if(valoracion != null) valoracionPerfil.setText(valoracionText);
        nombrePublicador.setText(publicador.getNombre() + " " + publicador.getApellido());

        nombrePlato.setText(producto.getNombre());
        //nombrePlatoLabel.setText("aaaaaaaaaaaaaaaaaaaaa \n patatas");

        if(publicador.getFoto() != null) fotoPerfilPub.setImageBitmap(service.pasarStringAImagen(publicador.getFoto()));
        perfilPubLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.pulsarPerfil(getContext(), publicador);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}