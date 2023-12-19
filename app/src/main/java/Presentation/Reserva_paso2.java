package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.IService;


public class Reserva_paso2 extends Fragment {
    private IService service;
    private Producto producto;
    private TextView nombrePlato, cantidad, precio, nombreUsuario, valoracion, direccion, rangoRecogida;

    public Reserva_paso2() { }

    public static Reserva_paso2 newInstance(Producto plato) {
        Reserva_paso2 fragment = new Reserva_paso2();
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
        nombrePlato = view.findViewById(R.id.nomPlato);
        cantidad = view.findViewById(R.id.cantidad);
        precio = view.findViewById(R.id.precio);
        nombreUsuario = view.findViewById(R.id.usuarioText);
        valoracion = view.findViewById(R.id.valorText);
        direccion = view.findViewById(R.id.textoDireccion);
        rangoRecogida = view.findViewById(R.id.rangoRecogidaText);

        Usuario publicador = producto.getUsuarioPublicador();
        nombrePlato.setText(producto.getNombre());
        //setUnidad();
        setPrecio();
        String nombreCompleto = publicador.getNombre() + " " + publicador.getApellido();
        nombreUsuario.setText(nombreCompleto);
        setValoracion(publicador.getValoracion());
        direccion.setText(producto.getDireccionRecogida());
        rangoRecogida.setText("Rango de hora: "+ producto.getHoraRecogida());
    }

   /* private void setUnidad() {
        cantidad.setText(producto.getNumRaciones() + "");

        if (producto.getNumRaciones() > 1) {
            unidad.setText("Unidades");
        } else {
            unidad.setText("Unidad");
        }
    }*/

    private void setPrecio() {
        Double cantidad2 = Double.parseDouble(cantidad.getText().toString());
        precio.setText(cantidad2 * producto.getPrecio() + " €");
    }

    private void setValoracion(int cantidad) {
        String valor = " ";
        for (int i = 0; i < cantidad; i++) {
            valor += "*";
        }
        valoracion.setText(valor);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserva_paso2, container, false);
    }

}