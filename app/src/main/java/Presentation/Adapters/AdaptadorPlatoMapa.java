package Presentation.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
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
import com.example.cucharon.Reserva;

import java.util.ArrayList;
import java.util.List;

import Negocio.IService;
import Negocio.Service;
import Presentation.Navegacion;
import Presentation.Perfil;
import Presentation.ReservaPaso1;

public class AdaptadorPlatoMapa extends RecyclerView.Adapter<AdaptadorPlatoMapa.PlatoMapaHolder> {
        private List<Producto> productos = new ArrayList<>();
        private Activity actividad;
        private IService servicio;

        public AdaptadorPlatoMapa(List<Producto> productos, Activity actividad) {
                this.servicio = Service.getService();
                this.productos = productos;
                this.actividad = actividad;
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

        public void setProductos(List<Producto> productos) {
                this.productos = productos;
                notifyDataSetChanged();
        }

        public class PlatoMapaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                ImageView imagenPlato ;
                TextView nombrePlato,horaRecogida,precioPlato;
                Producto plato;

                public PlatoMapaHolder(@NonNull View itemView) {
                        super(itemView);
                        imagenPlato= itemView.findViewById(R.id.imagenPlatoMapa);
                        nombrePlato= itemView.findViewById(R.id.nombrePlatoMapa);
                        horaRecogida = itemView.findViewById(R.id.horaRecogidaPlato);
                        precioPlato = itemView.findViewById(R.id.precioPlatoMapa);
                        itemView.setOnClickListener(this);
                }

                public void imprimirPlatoMapa(Producto producto) {
                        plato = producto;
                        imagenPlato.setImageBitmap(servicio.pasarStringAImagen(producto.getImagen()));
                        nombrePlato.setText(producto.getNombre());
                        horaRecogida.setText(producto.getHoraRecogida());
                        precioPlato.setText(producto.getPrecio()+"€");
                }

                @Override
                public void onClick(View view) {
                        //aquí se define el listener que espera al click de un plato.
                        Intent intent = new Intent(actividad, ReservaPaso1.class);
                        intent.putExtra("plato", plato.getIdProducto());
                        actividad.startActivity(intent);
                        actividad.finish();
                }
        }
}
