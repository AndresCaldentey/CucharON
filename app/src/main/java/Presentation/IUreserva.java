package Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Persistencia.ProductoRepository;
import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class IUreserva extends AppCompatActivity {

    TextView nombrePlato, nombreVendedor, horarioRecogida, valoracion, precio;
    Button btnReserva;
    ProductoRepository productoRepo;
    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        productoRepo = new ProductoRepository(SingletonConnection.getSingletonInstance());

        nombrePlato = findViewById(R.id.nombrePlato);
        nombreVendedor = findViewById(R.id.nombreVendedor);
        horarioRecogida = findViewById(R.id.horarioRecogida);
        valoracion = findViewById(R.id.textvaloracion);
        precio = findViewById(R.id.textPrecio);
        btnReserva = findViewById(R.id.btnReserva);

        Thread hilo = new Thread(() -> {
            producto = productoRepo.obtener(11);

            nombrePlato.setText(producto.getNombre());
            nombreVendedor.setText(producto.getUsuarioPublicador());
            precio.setText(producto.getPrecio() + "");
        });
        hilo.start();

    }

    public void clickReserva(View view)
    {
        //Se ha clickado el reserva
    }


}