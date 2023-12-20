package Presentation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cucharon.Producto;
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

        @Override
        protected List<Producto> doInBackground(Void... voids) {
            // Realiza la carga de productos en segundo plano
            return servicio.getProductosSinComprar();
        }

        @Override
        protected void onPostExecute(List<Producto> productos) {
            // Este método se ejecutará en el hilo principal después de que la carga haya terminado
            // Puedes continuar con el intent aquí o realizar cualquier otra acción necesaria
            PantalladaDeCargaInicio.this.productos = productos;  // Asigna la lista cargada
            Intent intent = new Intent(PantalladaDeCargaInicio.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
