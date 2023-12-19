package Presentation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView nombrePerfil, precioPlato, valoracionPerfil, nombrePublicador;
    private TextView nombrePlato;
    private ViewPager2 platosSliderHome;
    private CircleImageView fotoPerfilPub;
    private ToggleButton btnMapa, btnMasBarato, btnMasCaro, btnVendedorTop;
    private IService service;
    private List<Producto> productos;
    private List<ToggleButton> botones = new ArrayList<>();

    public Home() {}

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
        platosSliderHome.setAdapter(new AdaptadorHome(productos, getActivity()) );

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}