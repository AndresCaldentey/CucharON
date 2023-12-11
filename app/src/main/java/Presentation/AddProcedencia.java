package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cucharon.R;

import Presentation.Adapters.ClickCategoria;
import Presentation.Adapters.Pais;


public class AddProcedencia extends Fragment {


    ImageView cerrar;
    FragmentContainerView contenedorPaises;
    String paisNombre;
    public AddProcedencia() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cerrar = view.findViewById(R.id.cerrar);
        contenedorPaises = view.findViewById(R.id.contenedorPaises);
        ClickCategoria logicaPlato = new ClickCategoria() {
            @Override
            public void click(Pais pais) {
                paisNombre = pais.getNombre();
            }
        };
        ListaDesplegables paisesFragment = new ListaDesplegables(logicaPlato);
        paisesFragment.setAddPlatoFragment();
        getFragmentManager().beginTransaction().replace(R.id.contenedorPaises, paisesFragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_procedencia, container, false);
    }
}