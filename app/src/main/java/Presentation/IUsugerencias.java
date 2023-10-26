package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import Persistencia.ProductoRepository;
import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class IUsugerencias extends AppCompatActivity {

    LinearLayout sugerenciasLinearLayout;
    public static List<Producto> todosLosPlatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugerencias);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sugerenciasLinearLayout = findViewById(R.id.sugerenciasLinearLayout);

        //Intent intent = new Intent(IUsugerencias.this, IUreserva.class);
        //startActivity(intent);
        generarPlatos();

    }

    public void generarPlatos() {
        todosLosPlatos = new ArrayList<>();
        Thread hilo = new Thread(() -> {
            todosLosPlatos = new ProductoRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
            //Producto plato = todosLosPlatos.get(1);
            runOnUiThread(() -> { // Ejecuta las operaciones de UI en el hilo principal
                Context context = getApplicationContext();
                //ConstraintLayout constraintLayout = createPlato(plato, context);
                //sugerenciasLinearLayout.addView(constraintLayout);
                //sugerenciasLinearLayout.addView(createGap(context));
                for (Producto plato : todosLosPlatos) {
                    ConstraintLayout constraintLayout = createPlato(plato, context);
                    sugerenciasLinearLayout.addView(constraintLayout);
                    sugerenciasLinearLayout.addView(createGap(context));
                }
            });
        });
        hilo.start();
    }


    public LinearLayout createGap(Context context){
        LinearLayout linearLayout = new LinearLayout(context);

        // Establecer los atributos del LinearLayout
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Ancho
                80 // Alto en dp
        ));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }
    public ConstraintLayout createPlato(Producto plato, Context context) {
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                450
        ));

        constraintLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.plato_sugerencias));


        ImageView imageView = new ImageView(context);
        imageView.setId(View.generateViewId());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 39, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 37, context.getResources().getDisplayMetrics())
        ));

        imageView.setImageResource(R.drawable.dish);
        constraintLayout.addView(imageView);

        TextView textView1 = new TextView(context);
        textView1.setId(View.generateViewId());
        textView1.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, context.getResources().getDisplayMetrics())
        ));
        textView1.setText(plato.getNombre());
        constraintLayout.addView(textView1);

        View divider = new View(context);
        divider.setId(View.generateViewId());
        divider.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 332, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics())
        ));
        //divider.setBackgroundResource(android.R.attr.listDivider);
        constraintLayout.addView(divider);

        TextView textView2 = new TextView(context);
        textView2.setId(View.generateViewId());
        textView2.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 165, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics())
        ));
        textView2.setText("TextView");
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView2.setGravity(Gravity.CENTER);
        constraintLayout.addView(textView2);

        TextView textView3 = new TextView(context);
        textView3.setId(View.generateViewId());
        textView3.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 205, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, context.getResources().getDisplayMetrics())
        ));
        textView3.setText("TextView");
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        constraintLayout.addView(textView3);

        TextView textView4 = new TextView(context);
        textView4.setId(View.generateViewId());
        textView4.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textView4.setText("TextView");
        textView4.setTypeface(Typeface.create("sans-serif-condensed-light", Typeface.NORMAL));
        textView4.setTypeface(Typeface.DEFAULT_BOLD);
        constraintLayout.addView(textView4);

        // Crear un ConstraintSet para configurar las restricciones
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Restricciones para la imagen
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(imageView.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(imageView.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        // Restricciones para el primer TextView
        constraintSet.connect(textView1.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(textView1.getId(), ConstraintSet.BOTTOM, divider.getId(), ConstraintSet.TOP);
        constraintSet.connect(textView1.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(textView1.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        constraintSet.connect(divider.getId(), ConstraintSet.TOP, textView1.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(divider.getId(), ConstraintSet.BOTTOM, textView2.getId(), ConstraintSet.TOP);
        constraintSet.connect(divider.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(divider.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        // Restricciones para el segundo TextView
        constraintSet.connect(textView2.getId(), ConstraintSet.TOP, divider.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textView2.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textView2.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(textView2.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        // Restricciones para el tercer TextView
        constraintSet.connect(textView3.getId(), ConstraintSet.TOP, textView2.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textView3.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textView3.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(textView3.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        // Restricciones para el cuarto TextView
        constraintSet.connect(textView4.getId(), ConstraintSet.TOP, textView2.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textView4.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textView4.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(textView4.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        constraintSet.applyTo(constraintLayout);


        return constraintLayout;
    }
    public void buscarOnClick(View view) {
        Intent intent = new Intent( IUsugerencias.this, IUbuscar.class);
        startActivity(intent);
        finish();
    }
    public void posteoProductoOnClick(View view) {
        Intent intent = new Intent(IUsugerencias.this, IUposteoProducto.class);
        startActivity(intent);
        finish();
    }
    public void perfilOnClick(View view) {
        Intent intent = new Intent(IUsugerencias.this, IUperfil.class);
        startActivity(intent);
        // Restricciones para el divisor
       /* */
        finish();
    }


}