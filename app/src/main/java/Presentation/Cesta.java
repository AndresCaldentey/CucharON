package Presentation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.List;

import Negocio.IService;
import Negocio.Service;
import Presentation.Adapters.AdaptadorReservasEnCurso;
import Presentation.Adapters.AdaptadorReservasPrevias;

public class Cesta extends Fragment {
    private IService servicio;
    private AdaptadorReservasEnCurso adaptadorEnCurso;
    private AdaptadorReservasPrevias adaptadorPrevias;
    private Button btnReservas, btnPlatos;
    private List<Producto> reservasEnCurso, reservasPrevias, platosEnCurso, platosPrevios;

    public Cesta() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        servicio = Service.getService();
        RecyclerView recyclerViewEnCurso = view.findViewById(R.id.recyclerEnCursoCesto);
        RecyclerView recyclerViewPrevias = view.findViewById(R.id.recyclerPreviasCesta);
        btnReservas = view.findViewById(R.id.btnReservasCesta);
        btnPlatos = view.findViewById(R.id.btnPlatosCesta);

        /*Carga las listas enCurso y Previas*/
        reservasEnCurso = servicio.getProductoReservadoEnCurso();
        reservasPrevias = servicio.getProductoReservadoEntregado();
        platosEnCurso = servicio.getProductoPublicadoEnCurso();
        platosPrevios = servicio.getProductoPublicadoEntregado();

        /*Inicializar los recyclerViews*/
        adaptadorEnCurso = new AdaptadorReservasEnCurso(reservasEnCurso, getActivity());
        recyclerViewEnCurso.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        recyclerViewEnCurso.setAdapter(adaptadorEnCurso);
        adaptadorPrevias = new AdaptadorReservasPrevias(reservasPrevias, getActivity());
        recyclerViewPrevias.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        recyclerViewPrevias.setAdapter(adaptadorPrevias);

        /*Los listeners que cambian las listas*/
        btnReservas.setOnClickListener((view1) -> {
            botonSeleccionado(btnReservas, btnPlatos, reservasEnCurso, reservasPrevias);
        });

        btnPlatos.setOnClickListener((view1) -> {
            botonSeleccionado(btnPlatos, btnReservas, platosEnCurso, platosPrevios);
        });
    }

    private void botonSeleccionado(Button boton, Button btnADesactivar, List<Producto> enCurso, List<Producto> previa) {
        Drawable botonPulsado = ResourcesCompat.getDrawable(getResources(), R.drawable.boton_naranja, null);
        Drawable botonSinPulsar = ResourcesCompat.getDrawable(getResources(), R.drawable.boton_verde_borde, null);

        btnADesactivar.setBackground(botonSinPulsar);
        boton.setBackground(botonPulsado);

        adaptadorEnCurso.setProductos(enCurso);
        adaptadorEnCurso.notifyDataSetChanged();
        adaptadorPrevias.setProductos(previa);
        adaptadorPrevias.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cesta, container, false);
    }

}