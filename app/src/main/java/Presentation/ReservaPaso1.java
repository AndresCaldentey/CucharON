package Presentation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.IService;
import Negocio.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReservaPaso1 extends AppCompatActivity {
    private IService service;
    private Producto producto;
    private int platoId;


    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva_paso1);
        service = Service.getService();
        platoId = getIntent().getIntExtra("plato", -1);
        if(platoId == -1) finish();
        producto = service.getProductoById(platoId);

        TextView platoDesc = findViewById(R.id.platoDesc);
        TextView nombreUser = findViewById(R.id.nombreUsuarioReserva3);
        TextView valoracionUser = findViewById(R.id.valoracionReserva3);
        ImageView imagenUser = findViewById(R.id.fotoPerfilReserva3);
        TextView nombrePlato = findViewById(R.id.nombrePlatoReserva3);
        TextView precioPlato = findViewById(R.id.precioPlatoReserva3);
        CircleImageView imagenPlato = findViewById(R.id.imagenPlatoReserva3);
        Button reservaB = findViewById(R.id.reservaB);

        Usuario usuario = producto.getUsuarioPublicador();
        nombreUser.setText(usuario.getNombre());
        valoracionUser.setText(usuario.getValoracion()+"");
        imagenUser.setImageBitmap(service.pasarStringAImagen(usuario.getFoto()));
        nombrePlato.setText(producto.getNombre());
        precioPlato.setText(producto.getPrecio() + " euros");
        imagenPlato.setImageBitmap(service.pasarStringAImagen(producto.getImagen()) );
        platoDesc.setText(producto.getContenido());

    }

    public void reservarClick(View view){
        Intent intent = new Intent(this, ReservaPaso2.class);
        intent.putExtra("plato", platoId);
        startActivity(intent);
    }

    public void cerrarClick(View view){
        finish();
    }

    public void abrirEnMapa(){
        String label = producto.getDireccionRecogida();
        String uri = "geo:" + producto.getDireccionLatitud() + "," + producto.getDireccionLongitud() + "?q=" +  producto.getDireccionLatitud() + "," + producto.getDireccionLongitud() + "(" + label + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps"); // Asegura que se abra en Google Maps

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Si Google Maps no está instalado, maneja la situación de alguna manera
            Toast.makeText(this, "Google Maps no está instalado", Toast.LENGTH_SHORT).show();
        }
    }
    public void mostrarAlertaConDosOpciones(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Quieres abrir esta dirección en google maps?");
        builder.setMessage("Serás redirigido a otra aplicación");

        // Configurar botón positivo (Sí)
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en Sí
                // Puedes agregar lógica adicional aquí si es necesario
                abrirEnMapa();
                dialog.dismiss(); // Cierra la alerta

            }
        });

        // Configurar botón negativo (No)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Acciones al hacer clic en No
                // Puedes agregar lógica adicional aquí si es necesario
                dialog.dismiss(); // Cierra la alerta
            }
        });

        // Mostrar la alerta
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clickPerfil(View view){
        service.pulsarPerfil(ReservaPaso1.this, producto.getUsuarioPublicador());
    }



}