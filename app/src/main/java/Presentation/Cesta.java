package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.List;

import Negocio.Service;
import Presentation.Adapters.AdaptadorReservasEnCurso;
import Presentation.Adapters.AdaptadorReservasPrevias;

public class Cesta extends Fragment {
    private Service servicio;
    private RecyclerView recyclerViewEnCurso, recyclerViewPrevias;

    public Cesta() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        servicio = Service.getService();
        recyclerViewEnCurso = view.findViewById(R.id.recyclerEnCursoCesto);
        recyclerViewPrevias = view.findViewById(R.id.recyclerPreviasCesta);

        List<Producto> reservasEnCurso = servicio.getProductoReservadoEnCurso();
        recyclerViewEnCurso.setAdapter(new AdaptadorReservasEnCurso(reservasEnCurso));
        List<Producto> reservasPrevias = servicio.getProductoReservadoEntregado();
        recyclerViewPrevias.setAdapter(new AdaptadorReservasPrevias(reservasPrevias));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cesta, container, false);
    }
}