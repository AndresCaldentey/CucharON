package Presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentContainerView;

import com.example.cucharon.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import Negocio.Service;

public class Navegacion extends AppCompatActivity {
    private Service servicio;
    private ChipNavigationBar barraNav;
    private int previousIndex;
    FragmentContainerView mainFragmentContainer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barra_navegacion);
        servicio = Service.getService();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        barraNav = findViewById(R.id.barraNav);
        mainFragmentContainer = findViewById(R.id.mainFragmentContainer);
        barraNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if(i == R.id.search) {
                    previousIndex = R.id.search;
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Examinar()).commit();

                }
                if(i == R.id.add) {
                    barraNav.setItemSelected(previousIndex, true);
                    Intent intent = new Intent(Navegacion.this, IUAddPlato.class);
                    startActivity(intent);
                }
            }
        });
    }
}
