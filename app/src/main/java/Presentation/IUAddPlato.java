package Presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentContainerView;

import com.example.cucharon.Categoria;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Negocio.Service;
import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;

public class IUAddPlato extends AppCompatActivity implements OnDataPassListener {
    FragmentContainerView addPlatoFragmentMan;
    private int indexNav;
    ImageView botonSalir;
    private String nombre = "";
    private String descripcion = "";
    private Double precio = 0.0;
    private String imagen = "";
    private String direccion = "";
    private String horaRecogida = "";
    private String diaPreparacion = ""; //No se si se usara o se pondra por defecto
    private int racion = 0;
    private Double latitud = 0.0;
    private Double longitud = 0.0;
    private List<String> categorias = new ArrayList<>();
    Service service = Service.getService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plato_manager);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        botonSalir = findViewById(R.id.botonCerrar);
        addPlatoFragmentMan = findViewById(R.id.addPlatoFragmentMan);
        getSupportFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new NuevoPlato()).commit();


        View activityRootView = findViewById(android.R.id.content);

        // Configura un listener para cerrar el teclado cuando se toca fuera de un EditText
        activityRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Oculta el teclado cuando se toca fuera de un EditText
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });


    }


    @Override
    public void onDataPass(DataObject data) {
        // Decide cómo manejar los datos según su origen (Fragment1 o Fragment2)
        // Puedes almacenarlos en variables, en un modelo de datos, etc.
        // Aquí es donde gestionas los datos provenientes de los fragments.
        if (data.getSource().equals("nombre")) {
            nombre = data.getData();

        } else if (data.getSource().equals("descripcion")) {
            descripcion = data.getData();

        } else if (data.getSource().equals("imagen")) {
            imagen = data.getData();

        } else if (data.getSource().equals("categoria")) {
            categorias.add((data.getData()));

        } else if (data.getSource().equals("racion")) {
            racion = Integer.parseInt(data.getData());

        } else if (data.getSource().equals("hora")) {
            horaRecogida = data.getData();

        } else if (data.getSource().equals("precio")) {
            precio = Double.parseDouble(data.getData());

        } else if (data.getSource().equals("direccion")) {
            direccion = data.getData();

        } else if (data.getSource().equals("latitud")) {
            latitud = Double.parseDouble(data.getData());
        } else if (data.getSource().equals("longitud")) {
            longitud = Double.parseDouble(data.getData());

        }
    }

    public void botonCerrar(View view) {

        if (datosExisten()) {

            Producto producto = new Producto(0, nombre, descripcion, precio, horaRecogida, imagen, direccion, racion, service.getLoggedUser(), latitud, longitud);
            service.crearProducto(producto);

            Producto producto1 = service.getProductoById(producto.getIdProducto());
            for (String categoria : categorias) {

                ProductoCategoria productoCategoria = new ProductoCategoria(producto1, service.getCategoriaByName(categoria));
                service.guardarProductoCategoria(productoCategoria);
            }


            finish();

        } else {
            mostrarAlertaConDosOpciones();
        }

    }

    public boolean datosExisten() {
        if (nombre.isEmpty()) {

            return false;
        } else if (descripcion.isEmpty()) {

            return false;
        } else if (precio == 0.0) {

            return false;
        } else if (imagen.isEmpty()) {

            return false;
        } else if (direccion.isEmpty()) {

            return false;
        } else if (horaRecogida.isEmpty()) {

            return false;
        } else if (racion == 0) {

            return false;
        } else if (latitud == 0.0) {

            return false;
        } else if (longitud == 0.0) {

            return false;
        }

        return true;

    }

    private void mostrarAlertaConDosOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro que quieres cerrar?");
        builder.setMessage("Perderas todo tu progreso");

        // Configurar botón positivo (Sí)
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en Sí
                // Puedes agregar lógica adicional aquí si es necesario
                finish();
                dialog.dismiss(); // Cierra la alerta

            }
        });

        // Configurar botón negativo (No)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en No
                // Puedes agregar lógica adicional aquí si es necesario
                dialog.dismiss(); // Cierra la alerta
            }
        });

        // Mostrar la alerta
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
