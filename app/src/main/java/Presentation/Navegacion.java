package Presentation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;

import com.example.cucharon.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import Negocio.Service;

public class Navegacion extends AppCompatActivity {
    private Service servicio;
    private ChipNavigationBar barraNav;
    FragmentContainerView mainFragmentContainer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barra_navegacion);
        servicio = Service.getService();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        barraNav = findViewById(R.id.barraNav);
        mainFragmentContainer = findViewById(R.id.mainFragmentContainer);

    }
}
