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

public class AdaptadorReservasEnCurso extends RecyclerView.Adapter<AdaptadorReservasEnCurso.ReservasEnCursoHolder>{
    private List<Producto> productos = new ArrayList<>();
    private IService servicio;

    public AdaptadorReservasEnCurso(List<Producto> productos) {
        this.productos = productos;
        servicio = new Service();
    }

    public void setProductos(List<Producto> productos) {this.productos = productos;}

    @NonNull
    @Override
    public AdaptadorReservasEnCurso.ReservasEnCursoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdaptadorReservasEnCurso.ReservasEnCursoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reservas_en_curso, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorReservasEnCurso.ReservasEnCursoHolder holder, int position) {
        holder.visualizar(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }


    public class ReservasEnCursoHolder extends RecyclerView.ViewHolder {
        ImageView imagenPlato ;
        TextView textViewNombrePlato, textViewNombreUsuario, textViewRecogida;

        public ReservasEnCursoHolder(@NonNull View itemView) {
            super(itemView);
            imagenPlato= itemView.findViewById(R.id.imagenReservaEnCurso);
            textViewNombrePlato= itemView.findViewById(R.id.nombreReservaEnCurso);
            textViewNombreUsuario = itemView.findViewById(R.id.nombreUsuarioReservaEnCurso);
            textViewRecogida = itemView.findViewById(R.id.recogidaReservaEnCurso);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Se ha clickado
                }
            });
        }
        public void visualizar(Producto producto){
            imagenPlato.setImageBitmap(servicio.pasarStringAImagen(producto.getImagen()));
            textViewNombrePlato.setText(producto.getNombre());
            textViewNombreUsuario.setText(producto.getUsuarioPublicador().getNombre());
            textViewRecogida.setText(producto.getHoraRecogida());
        }
    }

}
