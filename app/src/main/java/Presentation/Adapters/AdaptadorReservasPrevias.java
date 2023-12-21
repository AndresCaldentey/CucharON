package Presentation.Adapters;

import android.app.Activity;
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
import Presentation.Mi_reserva_plato;
import Presentation.Navegacion;

public class AdaptadorReservasPrevias extends RecyclerView.Adapter<AdaptadorReservasPrevias.PreviasHolder> {
    private List<Producto> productos = new ArrayList<>();
    private IService servicio;
    private Activity actividad;
    int opcion;

    public AdaptadorReservasPrevias(List<Producto> productos, Activity actividad) {
        this.productos = productos;
        this.servicio = Service.getService();
        this.actividad = actividad;
    }

    public void setProductos(List<Producto> productos) {this.productos = productos;}
    public void setOpcion(int opcion) {this.opcion = opcion;}

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
        Producto plato;

        public PreviasHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombrePlato= itemView.findViewById(R.id.nombreReservaEnCurso);
            textViewNombreUsuario = itemView.findViewById(R.id.nombreUsuarioReservaEnCurso);
            textViewPrecio = itemView.findViewById(R.id.recogidaReservaEnCurso);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Se ha clickado
                    Navegacion navegacion = (Navegacion) actividad;
                    navegacion.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                            Mi_reserva_plato.newInstance(plato, opcion)).commit();
                }
            });
        }
        public void visualizar(Producto producto){
            plato = producto;
            textViewNombrePlato.setText(producto.getNombre());
            textViewNombreUsuario.setText(producto.getUsuarioPublicador().getNombre());
            textViewPrecio.setText(producto.getPrecio()+"€");
        }
    }

}
