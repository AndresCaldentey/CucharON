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

import Negocio.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class Mi_reserva_plato extends Fragment {
    private ImageView cerrar;
    private TextView categoria1, categoria2, titulo, raciones, direccion, recogida, precio, nombre_usu, valorarPlato;
    private Producto producto;
    private int opcion;
    private CircleImageView fotoPlato;
    public static final int TURESERVA = 0, TUPLATO = 1;

    Service servicio;

    public Mi_reserva_plato() { }

    public static Mi_reserva_plato newInstance(Producto plato, int opcion) {
        Mi_reserva_plato fragment = new Mi_reserva_plato();
        Bundle args = new Bundle();
        args.putSerializable("plato", plato);
        args.putInt("opcion", opcion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            producto = (Producto) getArguments().getSerializable("plato");
            opcion = getArguments().getInt("opcion");
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
        servicio = Service.getService();
        cerrar = view.findViewById(R.id.cerrarVerBusqueda);
        nombre_usu = view.findViewById(R.id.nombre_usu);
        precio = view.findViewById(R.id.precio_plato2);
        titulo = view.findViewById(R.id.tituloDelPlato);
        raciones = view.findViewById(R.id.raciones);
        categoria1 = view.findViewById(R.id.categoria1);
        categoria2 = view.findViewById(R.id.categoria2);
        direccion = view.findViewById(R.id.direccion);
        recogida = view.findViewById(R.id.recogida);
        valorarPlato = view.findViewById(R.id.valorarPlato);
        fotoPlato = view.findViewById(R.id.fotoPlato);

        ImageView detallesPlato = view.findViewById(R.id.detallesPlato);
        ImageView detallesReserva = view.findViewById(R.id.detallesReserva);

        fotoPlato.setImageBitmap(servicio.pasarStringAImagen(producto.getImagen()));

        titulo.setText(producto.getNombre());
        precio.setText(producto.getPrecio() + "€");
        raciones.setText(producto.getNumRaciones()+"");
        direccion.setText(producto.getDireccionRecogida());
        recogida.setText(producto.getHoraRecogida());
        nombre_usu.setText(producto.getUsuarioPublicador().getNombre());
        if(opcion == TUPLATO) {
            valorarPlato.setVisibility(View.GONE);
            detallesReserva.setVisibility(View.INVISIBLE);
            detallesPlato.setVisibility(View.VISIBLE);
        }
        if(opcion == TURESERVA){
            detallesPlato.setVisibility(View.INVISIBLE);
            detallesReserva.setVisibility(View.VISIBLE);
        }
        valorarPlato.setOnClickListener((view1) -> { mostrarDialogoCalificacion(); });
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

                dialog.dismiss();
            }
        });

        // Muestra el diálogo
        dialog.show();
    }

}