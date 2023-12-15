package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.List;

import Negocio.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorHome extends RecyclerView.Adapter<AdaptadorHome.HomeViewHolder> {
    List<Producto> productos;
    Service service;
    private int batchSize = 5; // Número de elementos a cargar en cada lote

    public AdaptadorHome(List<Producto> productos) {
        this.productos = productos;
        this.service = Service.getService();
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

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imagenPlato;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPlato = itemView.findViewById(R.id.imagenPlatoHome);
        }
        public void imprimir(Producto plato) {
            imagenPlato.setImageBitmap(service.pasarStringAImagen(plato.getImagen()));
        }
    }
}
