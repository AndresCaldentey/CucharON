package Presentation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        imagenUser.setImageBitmap(service.pasarStringAImagen(usuario.getFoto()) );
        nombrePlato.setText(producto.getNombre());
        precioPlato.setText(producto.getPrecio() + "€");
        imagenPlato.setImageBitmap(service.pasarStringAImagen(producto.getImagen()) );

        reservaB.setEnabled(!usuario.getEmail().equals(service.getLoggedUser().getEmail()));
        reservaB.setTextColor(reservaB.isEnabled() ? R.color.blancoDis : R.color.negroDis);

    }

    public void reservarClick(View view){
        Intent intent = new Intent(this, ReservaPaso2.class);
        intent.putExtra("plato", platoId);
        startActivity(intent);
    }



}