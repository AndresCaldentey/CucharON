package Presentation.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.List;

import Negocio.Service;
import Presentation.Navegacion;
import Presentation.ReservaPaso1;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorHome extends RecyclerView.Adapter<AdaptadorHome.HomeViewHolder> {
    List<Producto> productos;
    Service service;
    Activity actividad;
    private int batchSize = 5; // Número de elementos a cargar en cada lote

    public AdaptadorHome(List<Producto> productos, Activity actividad) {
        this.productos = productos;
        this.service = Service.getService();
        this.actividad = actividad;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorHome.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdaptadorHome.HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhome_plato, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorHome.HomeViewHolder holder, int position) {
        holder.imprimir(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imagenPlato;
        Producto plato;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPlato = itemView.findViewById(R.id.imagenPlatoHome);
            itemView.setOnClickListener(this);
        }

        public void imprimir(Producto p) {
            plato = p;
            imagenPlato.setImageBitmap(service.pasarStringAImagen(plato.getImagen()));
        }

        @Override
        public void onClick(View view) {
            //aquí se define el listener que espera al click de un plato.
            Navegacion navegacion = (Navegacion) actividad;
            Intent intent = new Intent(navegacion, ReservaPaso1.class);
            intent.putExtra("plato", plato.getIdProducto());
            navegacion.startActivity(intent);
        }

    }

}
