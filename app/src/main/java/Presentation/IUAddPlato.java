package Presentation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentContainerView;

import com.example.cucharon.R;

public class IUAddPlato extends AppCompatActivity {
    FragmentContainerView addPlatoFragmentMan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plato_manager);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        addPlatoFragmentMan = findViewById(R.id.addPlatoFragmentMan);
        getSupportFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new NuevoPlato()).commit();

    }
}
