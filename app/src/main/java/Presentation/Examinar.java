package Presentation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cucharon.R;

import Presentation.Adapters.ClickCategoria;
import Presentation.Adapters.Pais;

public class Examinar extends Fragment {
    private Button btnProcedencia, btnSabor;
    private FragmentContainerView contenedorExaminar;
    EditText searchText;
    ImageView searchB;

    public Examinar() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contenedorExaminar = view.findViewById(R.id.contenedorExaminar);
        btnProcedencia = view.findViewById(R.id.procedenciaB);
        btnSabor = view.findViewById(R.id.saborB);
        searchText = view.findViewById(R.id.searchText);
        searchB = view.findViewById(R.id.searchB);

        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchText.getText().toString().equals("")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nombre", searchText.getText().toString());
                    getParentFragmentManager().setFragmentResult("datos", bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new VerBusqueda()).commit();
                    if(getActivity() != null) {
                        if(getActivity() instanceof Navegacion) {
                            Navegacion activityActual = (Navegacion) getActivity();
                            activityActual.hidePerfil();
                        }
                    }
                }
            }
        });

        ClickCategoria logicaBusqueda = new ClickCategoria() {
            @Override
            public void click(Pais pais) {
                Bundle bundle = new Bundle();
                bundle.putString("categoria", pais.getNombre());
                bundle.putString("nombre", "");
                getParentFragmentManager().setFragmentResult("datos", bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new VerBusqueda()).commit();
                //Esconde el perfil de la pantalla
                if(getActivity() != null) {
                    if(getActivity() instanceof Navegacion) {
                       Navegacion activityActual = (Navegacion) getActivity();
                       activityActual.hidePerfil();
                    }
                }
            }
        };

        SaboresFragment.OnClickListener clickSabor = new SaboresFragment.OnClickListener() {
            @Override
            public void click(String sabor) {
                Bundle bundle = new Bundle();
                bundle.putString("categoria", sabor);
                bundle.putString("nombre", "");
                getParentFragmentManager().setFragmentResult("datos", bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new VerBusqueda()).commit();
                //Esconde el perfil de la pantalla
                if(getActivity() != null) {
                    if(getActivity() instanceof Navegacion) {
                        Navegacion activityActual = (Navegacion) getActivity();
                        activityActual.hidePerfil();
                    }
                }
                getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new VerBusqueda()).commit();
            }
        };

        getParentFragmentManager().beginTransaction().replace(R.id.contenedorExaminar, new ListaDesplegables(logicaBusqueda)).commit();

        btnProcedencia.setOnClickListener((view1) -> { botonSeleccionado(btnProcedencia, btnSabor, new ListaDesplegables(logicaBusqueda)); });

        btnSabor.setOnClickListener((view1) -> { botonSeleccionado(btnSabor, btnProcedencia, new SaboresFragment(clickSabor)); });
    }

    private void botonSeleccionado(Button boton, Button btnADesactivar, Fragment fragment) {
        Drawable botonPulsado = ResourcesCompat.getDrawable(getResources(), R.drawable.boton_naranja, null);
        Drawable botonSinPulsar = ResourcesCompat.getDrawable(getResources(), R.drawable.boton_verde_borde, null);

        btnADesactivar.setBackground(botonSinPulsar);
        boton.setBackground(botonPulsado);
        getParentFragmentManager().beginTransaction().replace(R.id.contenedorExaminar, fragment).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_examinar, container, false);
    }

}