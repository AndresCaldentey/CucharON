package Presentation.Adapters;

import android.graphics.Bitmap;
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

import Negocio.Service;

public class AdaptadorPlatoMapa extends RecyclerView.Adapter<AdaptadorPlatoMapa.PlatoMapaHolder> {
        List<Producto> productos = new ArrayList<>();
        LogicaMapa logicaMapa;
        Service sv;

        public AdaptadorPlatoMapa(List<Producto> productos, LogicaMapa logicaMapa) {
                this.productos = productos;
                this.logicaMapa = logicaMapa;
                sv = new Service();
        }

        @NonNull
        @Override
        public AdaptadorPlatoMapa.PlatoMapaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new AdaptadorPlatoMapa.PlatoMapaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plato_mapa, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorPlatoMapa.PlatoMapaHolder holder, int position) {
                holder.imprimirPlatoMapa(productos.get(position));
        }

        @Override
        public int getItemCount() {
                return productos.size();
        }

        public void setProductos(List<Producto> productos) {this.productos = productos;}

        public class PlatoMapaHolder extends RecyclerView.ViewHolder {
                ImageView imagenPlato ;
                TextView nombrePlato,horaRecogida,precioPlato;
                public PlatoMapaHolder(@NonNull View itemView) {
                        super(itemView);
                        imagenPlato= itemView.findViewById(R.id.imagenPlatoMapa);
                        nombrePlato= itemView.findViewById(R.id.nombrePlatoMapa);
                        horaRecogida = itemView.findViewById(R.id.horaRecogidaPlato);
                        precioPlato = itemView.findViewById(R.id.precioPlatoMapa);
                        itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        logicaMapa.click(productos.get(getAdapterPosition()));
                                }
                        });
                }
                public void imprimirPlatoMapa(Producto producto){
                        imagenPlato.setImageBitmap(sv.pasarStringAImagen(producto.getImagen()));
                        nombrePlato.setText(producto.getNombre());
                        horaRecogida.setText(producto.getHoraRecogida());
                        precioPlato.setText(producto.getPrecio()+"€");
                }
        }
        public interface LogicaMapa {
                public void click(Producto producto);
        }

}
