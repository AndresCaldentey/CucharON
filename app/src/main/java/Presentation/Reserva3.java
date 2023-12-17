package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;
import com.example.cucharon.reserva_paso2;

import java.util.List;

import Negocio.Service;
import Presentation.Adapters.AdaptadorPlato;

public class Reserva3 extends Fragment {
    private Service service;
    private TextView nombreUser, valoracionUser, nombrePlato, precioPlato, btnReserva;
    private ImageView imagenUser, imagenPlato;
    private Producto producto;

    public Reserva3() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = Service.getService();
        nombreUser = view.findViewById(R.id.nombreUsuarioReserva3);
        valoracionUser = view.findViewById(R.id.valoracionReserva3);
        imagenUser = view.findViewById(R.id.fotoPerfilReserva3);
        nombrePlato = view.findViewById(R.id.nombrePlatoReserva3);
        precioPlato = view.findViewById(R.id.precioPlatoReserva3);
        imagenPlato = view.findViewById(R.id.imagenPlatoReserva3);
        btnReserva = view.findViewById(R.id.btnReserva3);

        btnReserva.setOnClickListener((v) -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new reserva_paso2()).commit();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("Reserva3", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                producto = (Producto) result.getSerializable("platoReserva3");
                Usuario usuario = service.getLoggedUser();

                nombreUser.setText(usuario.getNombre());
                valoracionUser.setText(usuario.getValoracion()+"");
                imagenUser.setImageBitmap(service.pasarStringAImagen(usuario.getFoto()) );
                nombrePlato.setText(producto.getNombre());
                precioPlato.setText(producto.getPrecio() + "€");
                imagenPlato.setImageBitmap(service.pasarStringAImagen(producto.getImagen()) );
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reserva3, container, false);
    }

}