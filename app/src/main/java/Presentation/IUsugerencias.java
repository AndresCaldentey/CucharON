package Presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.Categoria;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.R;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Negocio.IService;
import Negocio.Service;
import Presentation.Adapters.AdaptadorPlato;

public class IUsugerencias extends AppCompatActivity {
    LinearLayout sugerenciasLinearLayout;
    public static List<Producto> todosLosPlatos;
    List<Producto> platos;
    IService servicio;
    List<Categoria> categoriasProducto = new ArrayList<>();
    List<Categoria> todasLasCategorias = new ArrayList<>();
    RelativeLayout popupCategorias;
    LinearLayout linearLayoutCategorias;
    Button botonFiltro;
    final Categoria[] categoriaEscogida = {null};
    RecyclerView rv1;
    AdaptadorPlato platosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugerencias);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //relacionado con la muestra del plato
        platos = new ArrayList<>();
        rv1 = findViewById(R.id.rv1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rv1.setLayoutManager(gridLayoutManager);
        platosAdapter = new AdaptadorPlato(IUreserva.class);
        rv1.setAdapter(platosAdapter);
        //--------------------------------------------------------------------------------

        servicio = Service.getService();
        popupCategorias = findViewById(R.id.categoriasPopup);
        linearLayoutCategorias = findViewById(R.id.linLayoutCategorias);
        botonFiltro = findViewById(R.id.botonFiltro);
        todosLosPlatos = servicio.getProductosSinComprar();
        todasLasCategorias = servicio.getAllCategorias();

        final String[] categoria = new String[0];
        //Intent intent = new Intent(IUsugerencias.this, IUreserva.class);
        //startActivity(intent);


        final int[] defaultPosition = {0};
         // Inicializamos con null, ya que aún no se ha seleccionado ninguna categoría

        botonFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos un nuevo array de categorías que incluye la opción "Todas las categorías"
                List<Categoria> categoriasConTodas = new ArrayList<>(todasLasCategorias);
                categoriasConTodas.add(0, new Categoria("Todas las categorías","no tiene")); // Ajusta esto según la estructura de tu clase Categoria

                // Creamos un array de nombres de categorías que incluye la opción "Todas las categorías"
                CharSequence[] categoriaNames = getCategoriaNames(categoriasConTodas);

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(IUsugerencias.this);
                builderSingle.setTitle("Filtra por categoría");
                builderSingle.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Al hacer clic en "OK", se guarda la categoría seleccionada en categoriaEscogida
                        categoriaEscogida[0] = categoriasConTodas.get(defaultPosition[0]); // Usar categoriasConTodas en lugar de todasLasCategorias
                        generarPlatos();
                        dialog.dismiss();


                    }
                });

                builderSingle.setSingleChoiceItems(categoriaNames, defaultPosition[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Al hacer clic en un elemento, actualizamos la posición seleccionada
                        defaultPosition[0] = which;
                    }
                });

                builderSingle.show();
            }
        });

        generarPlatos();

    }

    private CharSequence[] getCategoriaNames(List<Categoria> categorias) {
        CharSequence[] names = new CharSequence[categorias.size()];
        for (int i = 0; i < categorias.size(); i++) {
            names[i] = categorias.get(i).getNombre(); // Asumiendo que hay un método getNombre() en la clase Categoria
        }
        return names;
    }

    public void generarPlatos() {

        if(categoriaEscogida[0]== null || categoriaEscogida[0].getNombre() == "Todas las categorías") {

            platos = todosLosPlatos;
            actualizarPlatos(todosLosPlatos);

        }else {

            platos = new ProductoCategoria().getProductosByCategoria(categoriaEscogida[0].getNombre());

            if(platos.isEmpty()){
                Toast.makeText(this, "No hay productos con esta categoría", Toast.LENGTH_LONG).show(); // Escoger entre uno de estos
                showAlertDialog("Prueba con otra opción", "No hay productos con esta categoría"); // Escoger entre uno de estos
                categoriaEscogida[0] = null;
                actualizarPlatos(todosLosPlatos);
            }

            else {
                actualizarPlatos(platos);
            }

        }
    }



    public void actualizarPlatos(List<Producto> platos) {
        platosAdapter.setPlatos(platos);
        platosAdapter.notifyDataSetChanged();
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
        //Intent intent = new Intent(IUsugerencias.this, IUperfil.class);
        Intent intent = new Intent(IUsugerencias.this, Navegacion.class);
        startActivity(intent);
        // Restricciones para el divisor
       /* */
        finish();
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Código a ejecutar cuando se hace clic en OK
                        dialog.dismiss(); // Cierra el cuadro de diálogo
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }






}