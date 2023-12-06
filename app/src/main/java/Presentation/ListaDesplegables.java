package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.List;

import Presentation.Adapters.AdapterArea;
import Presentation.Adapters.Area;
import Presentation.Adapters.ClickCategoria;
import Presentation.Adapters.Pais;

public class ListaDesplegables extends Fragment {
    private RecyclerView listaDesplegables;
    List<Pais> paises;
    List<Area> areas;
    FragmentManager fragmentoExaminar;
    ClickCategoria logicaBusqueda;
    public ListaDesplegables() {
        // Required empty public constructor
    }
    public ListaDesplegables(ClickCategoria logicaBusqueda) {
        this.logicaBusqueda = logicaBusqueda;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaDesplegables = view.findViewById(R.id.listaDesplegables);
        initializeComponents();
        listaDesplegables.setAdapter(new AdapterArea(areas, paises, logicaBusqueda));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listaDesplegables.setLayoutManager(linearLayoutManager);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lista_desplegables_fragment, container, false);

    }

    void initializeComponents(){
        paises = new ArrayList<>();
        areas = new ArrayList<>();
        String[] areasNombre = {"Asiatica", "Mediterránea", "Latinoamericana", "Oriental"};
        String[] paisesAsia = {"China", "India" , "Japón", "Tailandia"};
        int[] imagenesAsia = {R.drawable.china, R.drawable.india, R.drawable.japonesa, R.drawable.tailandesa};

        String[] paisesMediterraneo = {"España", "Italia", "Grecia"};
        int[] imagenesMediterraneo = {R.drawable.spain, R.drawable.italiana, R.drawable.griega};

        String[] paisesLatam = {"Perú", "México", "Brasil"};
        int[] imagenesLatam = {R.drawable.peru, R.drawable.mexico, R.drawable.brasil};

        String[] paisesOriente = {"Marruecos", "Líbano", "Turquía", "Israel"};
        int[] imagenesOriente = {R.drawable.marruecos, R.drawable.liban, R.drawable.turquia, R.drawable.israel};


        List<Pais> paisesAux = new ArrayList<>();
        for(int i = 0; i < paisesAsia.length; i++) {
            paisesAux.add(new Pais(imagenesAsia[i], paisesAsia[i]));
        }
        areas.add(new Area(areasNombre[0], false));
        areas.get(0).setPaises(paisesAux);

        paisesAux = new ArrayList<>();
        for(int i = 0; i < paisesMediterraneo.length; i++) {
            paisesAux.add(new Pais(imagenesMediterraneo[i], paisesMediterraneo[i]));
        }
        areas.add(new Area(areasNombre[1], false));
        areas.get(1).setPaises(paisesAux);

        paisesAux = new ArrayList<>();
        for(int i = 0; i < paisesLatam.length; i++) {
            paisesAux.add(new Pais(imagenesLatam[i], paisesLatam[i]));
        }
        areas.add(new Area(areasNombre[2], false));
        areas.get(2).setPaises(paisesAux);

        paisesAux = new ArrayList<>();
        for(int i = 0; i < paisesOriente.length; i++) {
            paisesAux.add(new Pais(imagenesOriente[i], paisesOriente[i]));
        }
        areas.add(new Area(areasNombre[3], false));
        areas.get(3).setPaises(paisesAux);



    }
}