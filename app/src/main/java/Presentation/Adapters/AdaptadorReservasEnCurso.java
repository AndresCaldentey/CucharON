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
import Presentation.HomeMapa;
import Presentation.Mi_reserva_plato;
import Presentation.Navegacion;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorReservasEnCurso extends RecyclerView.Adapter<AdaptadorReservasEnCurso.ReservasEnCursoHolder>{
    private List<Producto> productos = new ArrayList<>();
    private IService servicio;
    private Activity activity;

    int opcion;
    public AdaptadorReservasEnCurso(List<Producto> productos, Activity activity) {
        this.productos = productos;
        this.servicio = Service.getService();
        this.activity = activity;
    }

    public void setProductos(List<Producto> productos) { this.productos = productos;}
    public void setOpcion(int opcion) { this.opcion = opcion; }

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
        CircleImageView imagenPlato ;
        TextView textViewNombrePlato, textViewNombreUsuario, textViewRecogida;
        Producto plato;


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
                    Navegacion actividad = (Navegacion) activity;
                    actividad.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                            Mi_reserva_plato.newInstance(plato, opcion)).commit();
                }
            });
        }
        public void visualizar(Producto producto){
            plato = producto;
            imagenPlato.setImageBitmap(servicio.pasarStringAImagen(producto.getImagen()));
            textViewNombrePlato.setText(producto.getNombre());
            textViewNombreUsuario.setText(producto.getUsuarioPublicador().getNombre());
            textViewRecogida.setText(producto.getHoraRecogida());
        }
    }

}
