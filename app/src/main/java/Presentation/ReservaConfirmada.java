package Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cucharon.R;

public class ReservaConfirmada extends AppCompatActivity {
    private ImageView cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva_confirmada);

        cerrar = findViewById(R.id.cerrar);
    }

    public void cerrar (View view){
        finish();
    }
}