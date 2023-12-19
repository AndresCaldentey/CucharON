package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.List;

import Negocio.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class SliderPlatosEnVenta extends RecyclerView.Adapter<SliderPlatosEnVenta.HolderPlatosVenta> {
    List<Producto> platos;
    Service servicio;

    public SliderPlatosEnVenta(List<Producto> platos) {
        this.platos = platos;
        servicio = Service.getService();
    }

    @NonNull
    @Override
    public SliderPlatosEnVenta.HolderPlatosVenta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderPlatosEnVenta.HolderPlatosVenta(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plato_en_venta, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderPlatosEnVenta.HolderPlatosVenta holder, int position) {
        holder.imprimir(platos.get(position));
    }

    @Override
    public int getItemCount() {
        if(platos == null) return 0;
        return platos.size();
    }

    public class HolderPlatosVenta extends RecyclerView.ViewHolder {
        TextView nombreLabel, cantidadValor, precioPlato;
        CircleImageView platoFoto;
        public HolderPlatosVenta(@NonNull View itemView) {
            super(itemView);
            nombreLabel = itemView.findViewById(R.id.nombreLabel);
            cantidadValor = itemView.findViewById(R.id.cantidadValor);
            precioPlato = itemView.findViewById(R.id.precioPlato);
            platoFoto = itemView.findViewById(R.id.platofoto);
        }
        public void imprimir(Producto producto) {
            nombreLabel.setText(producto.getNombre());
            cantidadValor.setText(producto.getNumRaciones() + " platos");
            precioPlato.setText(producto.getPrecio().toString() + " €");
            platoFoto.setImageBitmap(servicio.pasarStringAImagen(producto.getImagen()));
        }
    }
}
