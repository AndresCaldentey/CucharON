package Presentation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentContainerView;

import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.List;

import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;

public class IUAddPlato extends AppCompatActivity implements OnDataPassListener {
    FragmentContainerView addPlatoFragmentMan;

    private String nombre;
    private String contenido;
    private String precio;
    private String imagen;
    private String direccion;
    private String horaRecogida;
    private String diaPreparacion; //No se si se usara o se pondra por defecto
    private String direccionLatitud;
    private String direccionLongitud;
    private List<String> categorias = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plato_manager);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        addPlatoFragmentMan = findViewById(R.id.addPlatoFragmentMan);
        getSupportFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new NuevoPlato()).commit();

    }


    @Override
    public void onDataPass(DataObject data) {
        // Decide cómo manejar los datos según su origen (Fragment1 o Fragment2)
        // Puedes almacenarlos en variables, en un modelo de datos, etc.
        // Aquí es donde gestionas los datos provenientes de los fragments.
        if (data.getSource().equals("nombre")) {
            nombre = data.getData();
            System.out.println("------------------------ nombre en activity");
        } else if (data.getSource().equals("contenido")) {
            contenido = data.getData();
        } else if (data.getSource().equals("imagen")) {
            contenido = data.getData();
            System.out.println("------------------------ imagen en activity");
        } else if (data.getSource().equals("categoria")) {
           categorias.add(data.getData());
            System.out.println("------------------------ categoria en activity");
        }
    }
}
