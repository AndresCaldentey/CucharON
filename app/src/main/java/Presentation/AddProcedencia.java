package Presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cucharon.R;

import Presentation.Adapters.ClickCategoria;
import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;
import Presentation.Adapters.Pais;


public class AddProcedencia extends Fragment {

    private OnDataPassListener dataPassListener;
    ImageView cerrar;
    FragmentContainerView contenedorPaises;
    String paisNombre;
    Button siguiente;

    public AddProcedencia() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cerrar = view.findViewById(R.id.cerrar);
        contenedorPaises = view.findViewById(R.id.contenedorPaises);
        siguiente = view.findViewById(R.id.siguienteB5);
        ClickCategoria logicaPlato = new ClickCategoria() {
            @Override
            public void click(Pais pais) {
                paisNombre = pais.getNombre();
                System.out.println("------------------"+ paisNombre);
            }
        };
        ListaDesplegables paisesFragment = new ListaDesplegables(logicaPlato);
        paisesFragment.setAddPlatoFragment();
        getFragmentManager().beginTransaction().replace(R.id.contenedorPaises, paisesFragment).commit();

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (paisNombre == null) {
                    mostrarAlerta();
                } else {
                    //Ver a donde envia esto
                    sendPaisToActivity(paisNombre);
                    getParentFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new AddPlatoSabores()).commit();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (OnDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnDataPassListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_procedencia, container, false);
    }

    private void sendPaisToActivity(String data) {
        DataObject dataObject = new DataObject("categoria", data);
        dataPassListener.onDataPass(dataObject);
    }

    private void mostrarAlerta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Error");
        builder.setMessage("¡Debe escoger un lugar de procedencia!");

        // Configurar botón positivo (Aceptar)
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en Aceptar
                dialog.dismiss(); // Cierra la alerta
            }
        });

        // Mostrar la alerta
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}