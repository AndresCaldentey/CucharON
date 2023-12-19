package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Reserva;
import com.example.cucharon.Usuario;

import java.util.Date;

import Negocio.IService;
import Negocio.Service;


public class Reserva_paso2 extends Fragment {
    private IService service;
    private Producto producto;
    private TextView cantidad, unidad, precio, valoracion, nombrePlato, nombreUsuario, direccion, rangoRecogida;
    ImageView botonMas, botonMenos;
    int numCantidad = 1;
    Button botonReservar;
    Date horaRecodiga;

    public Reserva_paso2() {
    }

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
        nombreUsuario = view.findViewById(R.id.usuarioText);
        direccion = view.findViewById(R.id.textoDireccion);
        rangoRecogida = view.findViewById(R.id.rangoRecogidaText);
        cantidad = view.findViewById(R.id.cantidad);
        unidad = view.findViewById(R.id.unidad);
        precio = view.findViewById(R.id.precio);
        valoracion = view.findViewById(R.id.valorText);
        botonMas = view.findViewById(R.id.botonMas);
        botonMenos = view.findViewById(R.id.botonMenos);
        botonReservar = view.findViewById(R.id.reservarbtn);

        Usuario publicador = producto.getUsuarioPublicador();

        String nombreCompleto = publicador.getNombre() + " " + publicador.getApellido();

        nombreUsuario.setText(nombreCompleto);

        direccion.setText(producto.getDireccionRecogida());

        rangoRecogida.setText("Rango de hora: " + producto.getHoraRecogida());

        nombrePlato.setText(producto.getNombre());

        precio.setText(producto.getNumRaciones() * producto.getPrecio() + " €");

        setUnidades();

        //Inicializa valoracion
        String valor = " ";
        for (int i = 0; i < publicador.getValoracion(); i++) {
            valor += "*";
            valoracion.setText(valor);
        }
        //valoracion.setText(valor);

        botonMenos.setOnClickListener(view1 -> {

            numCantidad -= 1;
            cantidad.setText(numCantidad + "");

        });

        botonMas.setOnClickListener(view12 -> {

            numCantidad += 1;
            cantidad.setText(numCantidad + "");

        });

        botonReservar.setOnClickListener(view13 -> {

            Reserva reserva = new Reserva(0,numCantidad,producto,horaRecodiga, Service.getService().getLoggedUser());

        });
    }

    private void setUnidades() {
        //Inicializa la cantidad y las unidades
        cantidad.setText(producto.getNumRaciones() + "");
        if (producto.getNumRaciones() > 1) {
            unidad.setText("Unidades");
        } else {
            unidad.setText("Unidad");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserva_paso2, container, false);
    }

}