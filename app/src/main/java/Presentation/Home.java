package Presentation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.util.List;

import Negocio.CustomFontTextView;
import Negocio.Service;
import Presentation.Adapters.AdaptadorHome;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }
    TextView nombrePerfilHome, precioLabel, valoracionPerfil, nombrePublicador;
    CustomFontTextView nombrePlatoLabel;
    ViewPager2 platosSliderHome;
    CircleImageView fotoPerfilPub;
    ToggleButton mapaB, masBaratoB, masCaroB, vendedorTopB;

    Service service;

    List<Producto> productos;

    ToggleButton previousChecked = null;
    int previousColor;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Inicializacion variables programa

        nombrePerfilHome = view.findViewById(R.id.nombrePerfilHome);
        precioLabel = view.findViewById(R.id.precioLabel);
        valoracionPerfil = view.findViewById(R.id.valoracionPerfil);
        nombrePublicador = view.findViewById(R.id.nombrePublicador);

        nombrePlatoLabel = view.findViewById(R.id.nombrePlatoLabel);

        fotoPerfilPub = view.findViewById(R.id.fotoPerfilPub);

        platosSliderHome = view.findViewById(R.id.platosSliderHome);

        mapaB = view.findViewById(R.id.mapaB);
        masBaratoB = view.findViewById(R.id.masBaratoB);
        masCaroB = view.findViewById(R.id.masCaroB);
        vendedorTopB = view.findViewById(R.id.vendedorTopB);

        service = Service.getService();

        //Inicialización del Slider
        productos = service.getAllProducto();
        AdaptadorHome platosAdapter = new AdaptadorHome(productos);
        platosSliderHome.setAdapter(platosAdapter);

        //Codigo para hacer el slider con su animación de hacer los otros platos en chiquitito
        platosSliderHome.setClipToPadding(false);
        platosSliderHome.setClipChildren(false);
        platosSliderHome.setOffscreenPageLimit(3);
        platosSliderHome.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
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

        mapaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mapaB.isChecked()) return;
                actualizarBotones(mapaB, R.color.azulDis);
            }
        });

        masBaratoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(masBaratoB.isChecked()) return;
                actualizarBotones(masBaratoB, R.color.verdeOlivaDis);
            }
        });

        masCaroB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(masCaroB.isChecked()) return;
                actualizarBotones(masCaroB, R.color.plataDis);
            }
        });

        vendedorTopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vendedorTopB.isChecked()) return;
                actualizarBotones(vendedorTopB, R.color.naranjaDis);
            }
        });


    }


    @SuppressLint("ResourceAsColor")
    public void actualizarBotones(ToggleButton justCheckedB, int justCheckedColor){
        if(previousChecked == null ) {
            previousChecked = justCheckedB;
            previousColor = justCheckedColor;
            justCheckedB.setChecked(true);
            justCheckedB.setTextColor(R.color.negroDis);

        }
        if(!previousChecked.equals(justCheckedB) ){
            previousChecked.setChecked(false);
            previousChecked.setTextColor(previousColor);
            previousChecked = justCheckedB;
            previousColor = justCheckedColor;
            justCheckedB.setChecked(true);
            justCheckedB.setTextColor(R.color.negroDis);

        }
    }

    private void actualizarPantalla(Producto producto) {
        Usuario publicador = producto.getUsuarioPublicador();
        nombrePerfilHome.setText(service.getLoggedUser().getNombre());
        precioLabel.setText(producto.getPrecio().toString() + " euros");
        //int valoracion = publicador().getValoracion();
        //String valoracionText = String.valueOf('*').repeat(valoracion);
        //if(valoracion != null) valoracionPerfil.setText(valoracionText);
        nombrePublicador.setText(publicador.getNombre() + " " + publicador.getApellido());

        nombrePlatoLabel.setText(producto.getNombre());

        if(publicador.getFoto() != null) fotoPerfilPub.setImageBitmap(service.pasarStringAImagen(publicador.getFoto()));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}