package Presentation.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.List;

import Negocio.IService;
import Negocio.Service;
import Presentation.Navegacion;
import Presentation.Reserva3;

//El Adaptador es donde se almacena cada instancia de mostrar un plato y el holder es como se define cada instancia de mostrar plato.
public class AdaptadorPlato extends RecyclerView.Adapter<AdaptadorPlato.AdaptadorPlatoHolder>{
    //Aquí guardamos la lista de los platos que queremos mostrar
    List<Producto> platos = new ArrayList<>();
    Activity actividad;
    IService service;

    public AdaptadorPlato(List<Producto> platos, Activity actividad) {
        this.platos = platos;
        this.actividad = actividad;
        this.service = Service.getService();
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

    class AdaptadorPlatoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //inicializar las variables :)
        TextView nombre, recogida, precio, puntuacionPerfil;
        ImageView imagen, fotoPerfil;
        Producto plato;

        public AdaptadorPlatoHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreItemPlato);
            recogida = itemView.findViewById(R.id.recogidaItemPlato);
            precio = itemView.findViewById(R.id.precioItemPlato);
            imagen = itemView.findViewById(R.id.imagenItemPlato);
            fotoPerfil = itemView.findViewById(R.id.fotoPerfilItemPlato);
            puntuacionPerfil = itemView.findViewById(R.id.puntuacionPerfil);
            itemView.setOnClickListener(this);
        }

        public void imprimir(int position) {
            //Este método se encarga de imprimir el plato
            plato = platos.get(position);
            nombre.setText(plato.getNombre());
            recogida.setText(plato.getHoraRecogida());
            precio.setText(convertirAFormato(plato.getPrecio()) + "€");
            Bitmap image = service.pasarStringAImagen(plato.getImagen());
            imagen.setImageBitmap(image);
        }

        @Override
        public void onClick(View view) {
            //aquí se define el listener que espera al click de un plato.
            Navegacion navegacion = (Navegacion) actividad;
            navegacion.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, Reserva3.newInstance(plato)).commit();
        }
    }

}
