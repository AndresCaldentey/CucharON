package Presentation;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.io.Serializable;

import Negocio.*;

public class IUreserva extends AppCompatActivity {
    private Producto producto;
    private IService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        service = Service.getService();
        Serializable serializableExtra = getIntent().getSerializableExtra("Producto");
        if (serializableExtra != null) {
            int idProducto = (int) serializableExtra;
            producto = service.getProductoById(idProducto);
        }

        TextView nombrePlato = findViewById(R.id.nombrePlato);
        TextView nombreVendedor = findViewById(R.id.nombreVendedor);
        TextView horarioRecogida = findViewById(R.id.horarioRecogida);
        TextView textoHora = findViewById(R.id.textoHora);
        TextView precio = findViewById(R.id.textPrecio);
        ImageView imagenProducto = findViewById(R.id.imagenProducto);

        nombrePlato.setText(producto.getNombre());
        nombreVendedor.setText(producto.getUsuarioPublicador().getNombre());
        horarioRecogida.setText(producto.getHoraRecogida());
        textoHora.setText(producto.getHoraRecogida());
        precio.setText(producto.getPrecio() + "€");

        byte[] imageBytes = Base64.decode(producto.getImagen(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imagenProducto.setImageBitmap(decodedImage);
    }

    public void clickReserva(View view) {
        //Muestra un diologo en el que pide la confirmacion de la reserva del producto
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("¿Seguro que quieres reservar?");

        // Agregar botón Aceptar
        alertDialogBuilder.setPositiveButton("Aceptar", (dialog, which) -> {
            producto.setUsuarioComprador(service.getLoggedUser());
            service.actualizarProducto2(producto);
            finish();
            Intent intent = new Intent(IUreserva.this, IUsugerencias.class);
            startActivity(intent);
        });

        //Agregar boton Cancelar
        alertDialogBuilder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}