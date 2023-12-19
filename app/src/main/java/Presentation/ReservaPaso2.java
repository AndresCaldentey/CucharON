package Presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


public class ReservaPaso2 extends AppCompatActivity {
    private IService service;
    private Producto producto;
    private TextView cantidad, unidad, precio, valoracion, nombrePlato, nombreUsuario, direccion, rangoRecogida, disponibles;
    ImageView botonMas, botonMenos;
    int numCantidad = 1;
    Button botonReservar;
    Date horaRecodiga;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva_paso2);
        service = Service.getService();
        int platoId = getIntent().getIntExtra("plato", -1);
        if(platoId == -1) finish();
        producto = service.getProductoById(platoId);
        nombrePlato = findViewById(R.id.nomPlato);
        nombreUsuario = findViewById(R.id.usuarioText);
        direccion = findViewById(R.id.textoDireccion);
        rangoRecogida = findViewById(R.id.rangoRecogidaText);
        cantidad = findViewById(R.id.cantidad);
        unidad = findViewById(R.id.unidad);
        precio = findViewById(R.id.precio);
        valoracion = findViewById(R.id.valorText);
        botonMas = findViewById(R.id.botonMas);
        botonMenos = findViewById(R.id.botonMenos);
        botonReservar = findViewById(R.id.reservarbtn);
        disponibles = findViewById(R.id.unidDispTextView);

        Usuario publicador = producto.getUsuarioPublicador();

        String nombreCompleto = publicador.getNombre() + " " + publicador.getApellido();

        nombreUsuario.setText(nombreCompleto);

        cantidad.setText("1");

        disponibles.setText("Unidades disponibles: " + producto.getNumRaciones());

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
            if(numCantidad > 1){
                numCantidad -= 1;
                cantidad.setText(numCantidad+ "");
                setUnidades();
            }


        });

        botonMas.setOnClickListener(view12 -> {
            if(numCantidad < producto.getNumRaciones()){
                numCantidad += 1;
                cantidad.setText(numCantidad + "");
                setUnidades();
            }


        });

        botonReservar.setOnClickListener(view13 -> {

            Reserva reserva = new Reserva(0,numCantidad,producto,horaRecodiga, Service.getService().getLoggedUser());

        });
    }



    private void setUnidades() {
        //Inicializa la cantidad y las unidades
        if (numCantidad == 1) {
            unidad.setText("Unidad");
        } else {
            unidad.setText("Unidades");
        }
    }

}