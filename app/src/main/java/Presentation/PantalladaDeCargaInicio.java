package Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import Negocio.Service;

public class PantalladaDeCargaInicio extends AppCompatActivity {

    ImageView gifImagen;
    Service servicio;
    public static List<Producto> productos = new ArrayList<>();
    public static List<ProductoCategoria> productoCategorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_de_carga);
        gifImagen = findViewById(R.id.gifFondo);
        servicio = Service.getService();

        Glide.with(this).asGif().load(R.drawable.gift).into(gifImagen);



            new CargaProductosTask().execute();



    }

    public class CargaProductosTask extends AsyncTask<Void, Void, List<Producto>> {

        private String correo;

        @Override
        protected List<Producto> doInBackground(Void... voids) {
            // Obtener el correo electrónico de SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);
            correo = sharedPreferences.getString("token", "");

            // Setear el usuario logueado
            servicio.setLoggedUser(servicio.getUsuarioByEmail(correo));

            // Realizar la carga de productos en segundo plano
            return cargarProductos();
        }

        @Override
        protected void onPostExecute(List<Producto> productos) {
            // Este método se ejecutará en el hilo principal después de que la carga haya terminado
            // Puedes continuar con el intent aquí o realizar cualquier otra acción necesaria
            PantalladaDeCargaInicio.productos = productos;  // Asigna la lista cargada
            Intent intent = new Intent(PantalladaDeCargaInicio.this, Navegacion.class);
            startActivity(intent);
            finish();
        }
    }

    private List<Producto> cargarProductos() {
        // Realizar la carga de productos en segundo plano
        // Puedes realizar operaciones de base de datos directamente aquí
        productoCategorias = servicio.getAllProductoCategoria();
        return servicio.getProductosSinComprar();
    }
}
