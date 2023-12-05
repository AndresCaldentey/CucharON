package Presentation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cucharon.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Examinar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Examinar extends Fragment {

    Button procedenciaB, saborB;
    boolean procedenciaPress, saborPress;

    Drawable botonPulsado, botonSinPulsar;
    FragmentContainerView contenedorExaminar;

    public Examinar() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        procedenciaB = view.findViewById(R.id.procedenciaB);
        saborB = view.findViewById(R.id.saborB);
        botonPulsado = view.getResources().getDrawable(R.drawable.boton_naranja);
        botonSinPulsar = view.getResources().getDrawable(R.drawable.boton_verde_borde);
        saborPress = false;
        procedenciaPress = true;
        contenedorExaminar = view.findViewById(R.id.contenedorExaminar);
        procedenciaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saborPress){
                    saborPress = !saborPress;
                    saborB.setBackground(botonSinPulsar);
                    procedenciaPress = !procedenciaPress;
                    procedenciaB.setBackground(botonPulsado);
                    getFragmentManager().beginTransaction().replace(R.id.contenedorExaminar, new ListaDesplegables()).commit();

                }
            }
        });

        saborB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(procedenciaPress){
                    saborPress = !saborPress;
                    procedenciaPress = !procedenciaPress;
                    saborB.setBackground(botonPulsado);
                    procedenciaB.setBackground(botonSinPulsar);
                    getFragmentManager().beginTransaction().replace(R.id.contenedorExaminar, new SaboresFragment()).commit();
                }
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.contenedorExaminar, new ListaDesplegables()).commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_examinar, container, false);

    }
}