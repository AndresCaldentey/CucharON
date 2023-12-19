package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.IService;
import Negocio.Service;

public class Reserva_paso1 extends Fragment {
    private IService service;
    private Producto producto;

    public Reserva_paso1() { }

    public static Reserva_paso1 newInstance(Producto plato) {
        Reserva_paso1 fragment = new Reserva_paso1();
        Bundle args = new Bundle();
        args.putSerializable("plato", plato);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            producto = (Producto) getArguments().getSerializable("plato");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = Service.getService();
        TextView nombreUser = view.findViewById(R.id.nombreUsuarioReserva3);
        TextView valoracionUser = view.findViewById(R.id.valoracionReserva3);
        ImageView imagenUser = view.findViewById(R.id.fotoPerfilReserva3);
        TextView nombrePlato = view.findViewById(R.id.nombrePlatoReserva3);
        TextView precioPlato = view.findViewById(R.id.precioPlatoReserva3);
        ImageView imagenPlato = view.findViewById(R.id.imagenPlatoReserva3);
        TextView btnReserva = view.findViewById(R.id.btnReserva3);

        Usuario usuario = service.getLoggedUser();
        nombreUser.setText(usuario.getNombre());
        valoracionUser.setText(usuario.getValoracion()+"");
        imagenUser.setImageBitmap(service.pasarStringAImagen(usuario.getFoto()) );
        nombrePlato.setText(producto.getNombre());
        precioPlato.setText(producto.getPrecio() + "€");
        imagenPlato.setImageBitmap(service.pasarStringAImagen(producto.getImagen()) );

        btnReserva.setOnClickListener(v -> getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                Reserva_paso2.newInstance(producto)).commit());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reserva3, container, false);
    }

}