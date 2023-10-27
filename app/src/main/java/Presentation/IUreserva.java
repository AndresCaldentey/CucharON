package Presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.IService;
import Negocio.Servicio;
import Persistencia.ProductoRepository;
import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class IUreserva extends AppCompatActivity {
    IService service;
    TextView nombrePlato, nombreVendedor, horarioRecogida, valoracion, precio;
    ImageView imagenProducto;
    Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        service = new Servicio();

        //productoRepo = new ProductoRepository(SingletonConnection.getSingletonInstance());
        usuarioActual = MainActivity.usuarioActual;

        nombrePlato = findViewById(R.id.nombrePlato);
        nombreVendedor = findViewById(R.id.nombreVendedor);
        horarioRecogida = findViewById(R.id.horarioRecogida);
        valoracion = findViewById(R.id.textvaloracion);
        precio = findViewById(R.id.textPrecio);
        imagenProducto = findViewById(R.id.imagenProducto);

        Producto producto = service.getProductoById(11);
        nombrePlato.setText(producto.getNombre());
        nombreVendedor.setText(producto.getUsuarioPublicador());
        precio.setText(producto.getPrecio() + "");

        byte[] imageBytes = Base64.decode(producto.getImagen(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imagenProducto.setImageBitmap(decodedImage);
    }

    public void clickReserva(View view) { Alert("¿Seguro que quieres reservar?"); }

    public void Alert(String mensajeString) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(mensajeString);

        // Agregar botón "Aceptar"
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Producto producto = service.getProductoById(11);
                producto.setUsuarioComprador("pepe");
                service.actualizarProducto(producto);
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}