package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.List;

import Negocio.IService;
import Negocio.Service;

public class AdaptadorReservasPrevias extends RecyclerView.Adapter<AdaptadorReservasPrevias.PreviasHolder> {
    private List<Producto> productos = new ArrayList<>();
    private IService servicio;

    public AdaptadorReservasPrevias(List<Producto> productos) {
        this.productos = productos;
        servicio = new Service();
    }

    public void setProductos(List<Producto> productos) {this.productos = productos;}

    @NonNull
    @Override
    public AdaptadorReservasPrevias.PreviasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdaptadorReservasPrevias.PreviasHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reservas_previas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorReservasPrevias.PreviasHolder holder, int position) {
        holder.visualizar(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }


    public class PreviasHolder extends RecyclerView.ViewHolder {
        TextView textViewNombrePlato, textViewNombreUsuario, textViewPrecio;

        public PreviasHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombrePlato= itemView.findViewById(R.id.nombreReservaEnCurso);
            textViewNombreUsuario = itemView.findViewById(R.id.nombreUsuarioReservaEnCurso);
            textViewPrecio = itemView.findViewById(R.id.recogidaReservaEnCurso);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Se ha clickado
                }
            });
        }
        public void visualizar(Producto producto){
            textViewNombrePlato.setText(producto.getNombre());
            textViewNombreUsuario.setText(producto.getUsuarioPublicador().getNombre());
            textViewPrecio.setText(producto.getPrecio()+"€");
        }
    }

}
