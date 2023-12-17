package com.example.cucharon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import Presentation.Adapters.AdaptadorPlato;


public class reserva_paso2 extends Fragment {

    Producto producto;
    TextView nombrePlato, cantidad, unidad, precio, nombreUsuario, valoracion, direccion, rangoRecogida;

    public reserva_paso2() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombrePlato = view.findViewById(R.id.nomPlato);
        cantidad = view.findViewById(R.id.cantidad);
        unidad = view.findViewById(R.id.unidad);
        precio = view.findViewById(R.id.precio);
        nombreUsuario = view.findViewById(R.id.usuarioText);
        valoracion = view.findViewById(R.id.valorText);
        direccion = view.findViewById(R.id.textoDireccion);
        rangoRecogida = view.findViewById(R.id.rangoRecogidaText);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("datos2", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                producto = (Producto) result.getSerializable("plato");

                nombrePlato.setText(producto.getNombre());

                setUnidad();

                setPrecio();

                nombreUsuario.setText(producto.getUsuarioPublicador().getNombre() + " " + producto.getUsuarioPublicador().getApellido());

                setValoracion(producto.getUsuarioPublicador().getValoracion());

                direccion.setText(producto.getDireccionRecogida());

                rangoRecogida.setText("Rango de hora: "+ producto.getHoraRecogida());
            }
        });

    }

    private void setUnidad() {
        cantidad.setText(producto.getNumRaciones() + "");

        if (producto.getNumRaciones() > 1) {
            unidad.setText("Unidades");
        } else {
            unidad.setText("Unidad");
        }
    }

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