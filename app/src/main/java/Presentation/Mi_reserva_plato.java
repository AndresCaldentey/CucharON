package Presentation;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mi_reserva_plato#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mi_reserva_plato extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView cerrar;
    TextView categoria1;
    TextView categoria2;
    TextView titulo;
    TextView raciones;
    TextView direccion;
    TextView recogida;
    TextView precio;
    TextView nombre_usu;
    Button valorarPlato;

    public Mi_reserva_plato() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mi_reserva_plato.
     */
    // TODO: Rename and change types and number of parameters
    public static Mi_reserva_plato newInstance(String param1, String param2) {
        Mi_reserva_plato fragment = new Mi_reserva_plato();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_reserva_plato, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cerrar = view.findViewById(R.id.cerrarVerBusqueda);
        nombre_usu = view.findViewById(R.id.nombre_usu);
        precio = view.findViewById(R.id.precio_plato1);
        titulo = view.findViewById(R.id.tituloDelPlato);
        raciones = view.findViewById(R.id.raciones);
        categoria1 = view.findViewById(R.id.categoria1);
        categoria2 = view.findViewById(R.id.categoria2);
        direccion = view.findViewById(R.id.direccion);
        recogida = view.findViewById(R.id.recogida);
        valorarPlato = view.findViewById(R.id.valorarPlato);

        valorarPlato.setOnClickListener((view1) -> { mostrarDialogoCalificacion(); });


    }
    public void ponerDatosProducto(Producto producto){
        titulo.setText(producto.getNombre());
        precio.setText(producto.getPrecio().toString());
        raciones.setText(producto.getNumRaciones());
        direccion.setText(producto.getDireccionRecogida());
        recogida.setText(producto.getHoraRecogida());
        
    }

    private void mostrarDialogoCalificacion() {
        // Infla el diseño del diálogo
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.valoracion, null);

        // Encuentra las vistas en el diseño del diálogo
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);
        btnSubmit.setBackgroundResource(R.drawable.boton_naranja);

        // Crea el constructor de AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        // Crea el AlertDialog
        final AlertDialog dialog = builder.create();

        // Configura el clic del botón de aceptar
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene la calificación seleccionada
                float calificacion = ratingBar.getRating();

                // Puedes hacer algo con la calificación aquí (guardarla, enviarla a un servidor, etc.)
                Toast.makeText(getActivity(), "Calificación seleccionada: " + calificacion, Toast.LENGTH_SHORT).show();

                // Cierra el diálogo
                dialog.dismiss();
            }
        });

        // Muestra el diálogo
        dialog.show();
    }

}