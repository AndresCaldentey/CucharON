package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import java.util.List;

import Negocio.IService;
import Negocio.Service;

public class IUsugerencias extends AppCompatActivity {
    LinearLayout sugerenciasLinearLayout;
    public static List<Producto> todosLosPlatos;
    IService servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugerencias);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sugerenciasLinearLayout = findViewById(R.id.sugerenciasLinearLayout);
        servicio = Service.getService();
        //Intent intent = new Intent(IUsugerencias.this, IUreserva.class);
        //startActivity(intent);
        generarPlatos();
    }

    public void generarPlatos() {
        todosLosPlatos = servicio.getAllProducto();
        Context context = getApplicationContext();

        for (Producto plato : todosLosPlatos) {
            if(plato.getUsuarioComprador()==null){
                ConstraintLayout constraintLayout = createPlato(plato, context);
                sugerenciasLinearLayout.addView(constraintLayout);
                sugerenciasLinearLayout.addView(createGap(context));
            }
        }

        /*todosLosPlatos = new ArrayList<>();

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
        hilo.start();*/
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


        ImageView imageViewUsuario = new ImageView(context);
        imageViewUsuario.setId(View.generateViewId());
        imageViewUsuario.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics())
        ));
        imageViewUsuario.setImageResource(R.drawable.user);
        constraintLayout.addView(imageViewUsuario);

        ImageView imageViewPlato = new ImageView(context);
        imageViewPlato.setId(View.generateViewId());
        imageViewPlato.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics())
        ));

        //Obtiene la imagen
        Bitmap bitmap = servicio.pasarStringAImagen(plato.getImagen());

        // Establece el Bitmap en el ImageView
        imageViewPlato.setImageBitmap(bitmap);

        constraintLayout.addView(imageViewPlato);

        //TEXTO NOMBRE USUARIO PUBLICADOR
        TextView textView1 = new TextView(context);
        textView1.setId(View.generateViewId());
        textView1.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, context.getResources().getDisplayMetrics())
        ));

        Usuario usuario = servicio.getUsuarioByEmail(plato.getUsuarioPublicador());
        textView1.setText(usuario.getNombre());
        /*Thread hilo = new Thread (()->{
            Usuario usuario = new UsuarioRepository(SingletonConnection.getSingletonInstance()).getUserByEmail(plato.getUsuarioPublicador());
            textView1.setText(usuario.getNombre());
        });
        hilo.start();*/
        constraintLayout.addView(textView1);

        View divider = new View(context);
        divider.setId(View.generateViewId());
        divider.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 304, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics())
        ));
        divider.setBackgroundColor(getResources().getColor(android.R.color.black));
        constraintLayout.addView(divider);

        //TEXTO NOMBRE PLATO
        TextView textViewNombrePlato = new TextView(context);
        textViewNombrePlato.setId(View.generateViewId());
        textViewNombrePlato.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 305, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics())
        ));

        textViewNombrePlato.setText(plato.getNombre());
        textViewNombrePlato.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textViewNombrePlato.setGravity(Gravity.CENTER);
        constraintLayout.addView(textViewNombrePlato);

        //TEXTO DIRECCION RECOGIDA
        TextView textViewDirRecogida = new TextView(context);
        textViewDirRecogida.setId(View.generateViewId());
        textViewDirRecogida.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, context.getResources().getDisplayMetrics())
        ));
        textViewDirRecogida.setText(plato.getDireccionRecogida());
        textViewDirRecogida.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        constraintLayout.addView(textViewDirRecogida);

        //TEXTO PRECIO PLATO
        TextView textViewPrecio = new TextView(context);
        textViewPrecio.setId(View.generateViewId());
        textViewPrecio.setLayoutParams(new ViewGroup.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, context.getResources().getDisplayMetrics())
        ));
        textViewPrecio.setText(plato.getPrecio().toString()+"€");
        textViewPrecio.setTypeface(Typeface.create("sans-serif-condensed-light", Typeface.NORMAL));
        textViewPrecio.setTypeface(Typeface.DEFAULT_BOLD);
        constraintLayout.addView(textViewPrecio);

        // Crear un ConstraintSet para configurar las restricciones
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // Restricciones para la imagen
        constraintSet.connect(imageViewPlato.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(imageViewPlato.getId(), ConstraintSet.BOTTOM, divider.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(imageViewPlato.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(imageViewPlato.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        constraintSet.connect(imageViewUsuario.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(imageViewUsuario.getId(), ConstraintSet.BOTTOM, divider.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(imageViewUsuario.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(imageViewUsuario.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        // Restricciones para el primer TextView NOMBRE PUBLICADOR
        constraintSet.connect(textView1.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
        constraintSet.connect(textView1.getId(), ConstraintSet.BOTTOM, divider.getId(), ConstraintSet.TOP);
        constraintSet.connect(textView1.getId(), ConstraintSet.START, imageViewUsuario.getId(), ConstraintSet.END);
        constraintSet.connect(textView1.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        constraintSet.connect(divider.getId(), ConstraintSet.TOP, textViewNombrePlato.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(divider.getId(), ConstraintSet.BOTTOM, textViewDirRecogida.getId(), ConstraintSet.TOP);
        constraintSet.connect(divider.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(divider.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        // Restricciones para el segundo TextView NOMBRE PLATO
        constraintSet.connect(textViewNombrePlato.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewNombrePlato.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewNombrePlato.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(textViewNombrePlato.getId(), ConstraintSet.END, divider.getId(), ConstraintSet.END);

        // Restricciones para el tercer TextView DIRECCION RECOGIDA
        constraintSet.connect(textViewDirRecogida.getId(), ConstraintSet.TOP, textViewNombrePlato.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewDirRecogida.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewDirRecogida.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(textViewDirRecogida.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        // Restricciones para el cuarto TextView PRECIO PLATO
        constraintSet.connect(textViewPrecio.getId(), ConstraintSet.TOP, textViewDirRecogida.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewPrecio.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(textViewPrecio.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(textViewPrecio.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END);

        constraintSet.applyTo(constraintLayout);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes manejar el evento de clic en el ConstraintLayout
                IUreserva reserva = new IUreserva();
                Intent intent = new Intent(IUsugerencias.this,IUreserva.class);
                intent.putExtra("producto",plato);
                startActivity(intent);
            }
        });

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