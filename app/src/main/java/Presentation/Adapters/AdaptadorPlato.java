package Presentation.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DecimalFormat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.util.ArrayList;
import java.util.List;

import Negocio.IService;
import Negocio.Service;
import Presentation.Navegacion;
import Presentation.ReservaPaso1;

//El Adaptador es donde se almacena cada instancia de mostrar un plato y el holder es como se define cada instancia de mostrar plato.
public class AdaptadorPlato extends RecyclerView.Adapter<AdaptadorPlato.AdaptadorPlatoHolder>{
    //Aquí guardamos la lista de los platos que queremos mostrar
    List<Producto> platos = new ArrayList<>();
    Activity actividad;
    IService service;
    OnClickPerfilListener logicaClickPerfil;

    public AdaptadorPlato(List<Producto> platos, Activity actividad, OnClickPerfilListener logicaCPerfil) {
        this.platos = platos;
        this.actividad = actividad;
        this.service = Service.getService();
        logicaClickPerfil = logicaCPerfil;
    }

    public void setPlatos (List<Producto> platos) { this.platos = platos; }

    @NonNull
    @Override
    public AdaptadorPlato.AdaptadorPlatoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Este método es obligatorio por el extends y permite la relación entre el holder(cada item por separado) y la vista recyclerView
        return new AdaptadorPlatoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemplato, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPlato.AdaptadorPlatoHolder holder, int position) {
        //Aquí se imprimen los platos en pantalla
        holder.imprimir(position);
    }

    @Override
    public int getItemCount() {
        //otro metodo necesario por el extends
        if(platos == null) return 0;
        return platos.size();
    }

    public String convertirAFormato(double numero) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(numero);
    }

    class AdaptadorPlatoHolder extends RecyclerView.ViewHolder {
        //inicializar las variables :)
        TextView nombre, recogida, precio, puntuacionPerfil;
        ImageView imagen, fotoPerfil;
        Producto plato;
        LinearLayout layoutPerfil;

        public AdaptadorPlatoHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreItemPlato);
            recogida = itemView.findViewById(R.id.recogidaItemPlato);
            precio = itemView.findViewById(R.id.precioItemPlato);
            imagen = itemView.findViewById(R.id.imagenItemPlato);
            fotoPerfil = itemView.findViewById(R.id.fotoPerfilItemPlato);
            puntuacionPerfil = itemView.findViewById(R.id.puntuacionPerfil);
            layoutPerfil = itemView.findViewById(R.id.layoutPerfil);
        }

        public void imprimir(int position) {
            //Este método se encarga de imprimir el plato
            plato = platos.get(position);
            nombre.setText(plato.getNombre());
            recogida.setText(plato.getHoraRecogida());
            precio.setText(convertirAFormato(plato.getPrecio()) + "€");
            Bitmap image = service.pasarStringAImagen(plato.getImagen());
            imagen.setImageBitmap(image);
            if(plato.getUsuarioPublicador().getFoto() != null) fotoPerfil.setImageBitmap(service.pasarStringAImagen(plato.getUsuarioPublicador().getFoto()));
            layoutPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logicaClickPerfil.click(plato.getUsuarioPublicador());
                }
            });
            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pulsar();
                }
            });

        }


        private void pulsar(){
            Navegacion navegacion = (Navegacion) actividad;
            Intent intent = new Intent(navegacion, ReservaPaso1.class);
            intent.putExtra("plato", plato.getIdProducto());
        }
    }
    public interface OnClickPerfilListener{
        public void click(Usuario usuario);
    }


}
