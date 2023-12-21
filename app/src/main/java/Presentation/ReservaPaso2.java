package Presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Reserva;
import com.example.cucharon.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Negocio.IService;
import Negocio.Service;


public class ReservaPaso2 extends AppCompatActivity {
    private IService service;
    private Producto producto;
    private TextView cantidad, unidad, precio, valoracion, nombrePlato, nombreUsuario,
            direccion, rangoRecogida, disponibles, horaRecogidaText, minutosRegogidaText;
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
        if (platoId == -1) finish();
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
        horaRecogidaText = findViewById(R.id.horaRecogidaText);
        minutosRegogidaText = findViewById(R.id.minutosRecogidaText);


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
            if (numCantidad > 1) {
                numCantidad -= 1;
                cantidad.setText(numCantidad + "");
                setUnidades();
            }


        });

        botonMas.setOnClickListener(view12 -> {
            if (numCantidad < producto.getNumRaciones()) {
                numCantidad += 1;
                cantidad.setText(numCantidad + "");
                setUnidades();
            }


        });

        botonReservar.setOnClickListener(view13 -> {

            String formadorDehora = horaRecogidaText.getText().toString() + ":" + minutosRegogidaText.getText().toString();

            try {
                if (estaEnRango(formadorDehora, producto.getHoraRecogida().toString())) {

                    //Reserva reserva = new Reserva(0, numCantidad, producto, horaRecodiga, Service.getService().getLoggedUser());
                    producto.setUsuarioComprador(service.getLoggedUser());
                    producto.setRacionesReservadas(numCantidad);
                    producto.setHoraReserva(formadorDehora);
                    service.actualizarProducto(producto);
                    Intent intent = new Intent(ReservaPaso2.this, ReservaConfirmada.class);
                    startActivity(intent);
                    finish();
                } else {
                    mostrarAlerta();
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


        });
    }

    public static boolean estaEnRango(String horaAComprobar, String rangoHoras) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        // Parsear las horas del rango
        String[] partes = rangoHoras.split(" - ");
        Date inicio = sdf.parse(partes[0]);
        Date fin = sdf.parse(partes[1]);

        // Parsear la hora a comprobar
        Date horaComprobar = sdf.parse(horaAComprobar);

        // Verificar si la hora a comprobar está en el rango
        return horaComprobar.after(inicio) && horaComprobar.before(fin);
    }

    private void setUnidades() {
        //Inicializa la cantidad y las unidades
        if (numCantidad == 1) {
            unidad.setText("Unidad");
        } else {
            unidad.setText("Unidades");
        }
    }

    private void mostrarAlerta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("¡la hora no esta en el rango correcto!");

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

    public void cerrarClick(View view) {
        finish();
    }

}