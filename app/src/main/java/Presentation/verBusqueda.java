package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.R;

import java.util.List;

import Negocio.Service;
import Presentation.Adapters.AdaptadorPlato;

public class verBusqueda extends Fragment {


    TextView pais_txt;
    ImageView cerrar;
    RecyclerView listaPlatos;
    Service service;
    String categoria;
    List<Producto> platos;

    AdaptadorPlato platosAdapter;
    AdaptadorPlato.ClickPlato logicaPlato;

    public verBusqueda() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pais_txt = view.findViewById(R.id.pais_en_tu_tv);
        cerrar = view.findViewById(R.id.cerrar);
        service = Service.getService();

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Examinar()).commit();
            }
        });
        listaPlatos = view.findViewById(R.id.listaPlatos);

         logicaPlato = new AdaptadorPlato.ClickPlato() {
            @Override
            public void click(Producto plato) {
                Toast.makeText(view.getContext(), plato.getNombre(), Toast.LENGTH_SHORT).show();
            }
        };






    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("datos", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                categoria = result.getString("categoria");
                pais_txt.setText(categoria + " en tu");
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                listaPlatos.setLayoutManager(gridLayoutManager);
                platos = service.buscarPorCategoria(categoria);
                platosAdapter = new AdaptadorPlato(platos, logicaPlato);
                listaPlatos.setAdapter(platosAdapter);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_busqueda, container, false);
    }
}