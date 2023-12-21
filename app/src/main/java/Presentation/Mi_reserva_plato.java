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

import com.example.cucharon.Categoria;
import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.List;

import Negocio.IService;
import Negocio.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class Mi_reserva_plato extends Fragment {
    public static final int TURESERVA = 0, TUPLATO = 1;
    private IService servicio;
    private Producto producto;
    private int opcion;

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
        if(getActivity() != null) {
            if(getActivity() instanceof Navegacion) {
               ((Navegacion) getActivity()).hidePerfil();
            }
        }
        servicio = Service.getService();
        List<Categoria> productoCategorias = servicio.getCategoriasPorProducto(producto);

        ImageView cerrar = view.findViewById(R.id.cerrarReserva);
        CircleImageView fotoPlato = view.findViewById(R.id.fotoPlato);
        TextView nombre_usu = view.findViewById(R.id.nombre_usu);
        TextView precio = view.findViewById(R.id.precio_plato2);
        TextView titulo = view.findViewById(R.id.tituloDelPlato);
        TextView raciones = view.findViewById(R.id.raciones);
        TextView categoria1 = view.findViewById(R.id.categoria1);
        TextView categoria2 = view.findViewById(R.id.categoria2);
        TextView direccion = view.findViewById(R.id.direccion);
        TextView recogida = view.findViewById(R.id.recogida);
        TextView valorarPlato = view.findViewById(R.id.valorarPlato);
        ImageView detallesPlato = view.findViewById(R.id.detallesPlato);
        ImageView detallesReserva = view.findViewById(R.id.detallesReserva);

        for (Categoria c:productoCategorias) {
            if(c.getEsPais()){
                categoria1.setText(c.getNombre());
            }else {
                categoria2.setText(c.getNombre());
            }
        }

        fotoPlato.setImageBitmap(servicio.pasarStringAImagen(producto.getImagen()));

        titulo.setText(producto.getNombre());
        double precioTotal = producto.getPrecio() * producto.getRacionesReservadas();
        precio.setText(precioTotal + "€");
        raciones.setText(producto.getRacionesReservadas()+" raciones");
        direccion.setText(producto.getDireccionRecogida());
        recogida.setText(producto.getHoraRecogida());
        nombre_usu.setText(producto.getUsuarioPublicador().getNombre());
        if(opcion == TUPLATO) {
            valorarPlato.setVisibility(View.GONE);
            detallesReserva.setVisibility(View.GONE);
            detallesPlato.setVisibility(View.VISIBLE);
        }
        if(opcion == TURESERVA){
            detallesPlato.setVisibility(View.GONE);
            detallesReserva.setVisibility(View.VISIBLE);
            if(producto.getValoracion() >= 0) { valorarPlato.setVisibility(View.GONE); }
        }

        cerrar.setOnClickListener((view1) -> getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                new Cesta()).commit());

        if(producto.getValoracion() == -1) valorarPlato.setOnClickListener((view1) -> mostrarDialogoCalificacion() );

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
        btnSubmit.setOnClickListener((view -> {
            float calificacion = ratingBar.getRating();
            int calificacionInt = (int) calificacion;
            producto.setValoracion(calificacionInt);
            servicio.setValoracionProducto(producto, calificacionInt);
            getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                    new Cesta()).commit();
            dialog.dismiss();
        }));

        // Muestra el diálogo
        dialog.show();
    }

}