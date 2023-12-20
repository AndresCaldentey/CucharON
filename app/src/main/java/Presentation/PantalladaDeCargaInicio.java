package Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cucharon.R;

public class PantalladaDeCargaInicio extends AppCompatActivity {

    ImageView gifImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_de_carga);
        gifImagen = findViewById(R.id.gifFondo);

        Glide.with(this).asGif().load(R.drawable.gift).into(gifImagen);


       new Handler().postDelayed(new Runnable() {
            @Override
           public void run() {
                Intent intent = new Intent(PantalladaDeCargaInicio.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);


    }
}
