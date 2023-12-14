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

import com.example.cucharon.Plato_publicado;
import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.List;

import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;

public class IUAddPlato extends AppCompatActivity implements OnDataPassListener {
    FragmentContainerView addPlatoFragmentMan;
    private int indexNav;
    ImageView botonSalir;
    private String nombre = "";
    private String descripcion = "";
    private String precio = "";
    private String imagen = "";
    private String direccion = "";
    private String horaRecogida = "";
    private String diaPreparacion = ""; //No se si se usara o se pondra por defecto
    private String direccionLatitud = "";
    private String direccionLongitud = "";
    private String racion = "";
    private List<String> categorias = new ArrayList<>();

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
            System.out.println("nombre en activity");
        } else if (data.getSource().equals("descripcion")) {
            descripcion = data.getData();
            System.out.println("descripcion en activity");
        } else if (data.getSource().equals("imagen")) {
            imagen = data.getData();
            System.out.println("imagen en activity");
        } else if (data.getSource().equals("categoria")) {
            categorias.add(data.getData());
            System.out.println("categoria en activity");
        } else if (data.getSource().equals("racion")) {
            racion = data.getData();
            System.out.println("racion en activity");
        } else if (data.getSource().equals("hora")) {
            horaRecogida = data.getData();
            System.out.println("hora en activity");
        } else if (data.getSource().equals("precio")) {
            precio = data.getData();
            System.out.println("precio en activity");
        } else if (data.getSource().equals("direccion")) {
            direccion = data.getData();
            System.out.println("direccion en activity");
        }
    }

    public void botonCerrar(View view) {
        System.out.println("Funcionaaaaa");
        if (datosExisten()) {
            //guardar producto
        } else {
            mostrarAlertaConDosOpciones();
        }
        System.out.println("No tantoooo");
    }

    public boolean datosExisten() {
        if (nombre.isEmpty()) {
            return false;
        } else if (descripcion.isEmpty()) {
            return false;
        } else if (precio.isEmpty()) {
            return false;
        } else if (imagen.isEmpty()) {
            return false;
        } else if (direccion.isEmpty()) {
            return false;
        } else if (horaRecogida.isEmpty()) {
            return false;
        } else if (racion.isEmpty()) {
            return false;
        } else if (direccionLatitud.isEmpty()) {
            return false;
        } else if (direccionLongitud.isEmpty()) {
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
