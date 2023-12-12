package Presentation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import Presentation.IUreserva;

//El Adaptador es donde se almacena cada instancia de mostrar un plato y el holder es como se define cada instancia de mostrar plato.
public class AdaptadorPlato extends RecyclerView.Adapter<AdaptadorPlato.AdaptadorPlatoHolder>{

    //Aquí guardamos la lista de los platos que queremos mostrar
    List<Producto> platos = new ArrayList<>();
    Context context;
    IService service;
    ClickPlato logicaPlato;
    public AdaptadorPlato(ClickPlato logicaPlato) {
        service = Service.getService();
    }
    public AdaptadorPlato(List<Producto> platos, ClickPlato logicaPlato) {

        this.platos = platos;
        this.logicaPlato = logicaPlato;
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
        TextView nomP, recP, precioP, puntuacionPerfil;
        ImageView platoI, fotoDPerfil;
        Producto plato;
        public AdaptadorPlatoHolder(@NonNull View itemView) {
            super(itemView);
            nomP = itemView.findViewById(R.id.nomP);
            recP = itemView.findViewById(R.id.recP);
            precioP = itemView.findViewById(R.id.precioP);
            platoI = itemView.findViewById(R.id.platoI);
            fotoDPerfil = itemView.findViewById(R.id.fotoDPerfil);
            puntuacionPerfil = itemView.findViewById(R.id.puntuacionPerfil);
            itemView.setOnClickListener(this);
        }
        public void imprimir(int position) {
            //Este método se encarga de imprimir el plato
            nomP.setText(platos.get(position).getNombre());
            recP.setText(platos.get(position).getHoraRecogida());
            precioP.setText(convertirAFormato(platos.get(position).getPrecio()) + "€");
            Bitmap image = service.pasarStringAImagen(platos.get(position).getImagen());
            platoI.setImageBitmap(image);
            plato = platos.get(position);
        }

        @Override
        public void onClick(View view) {
            //aquí se define el listener que espera al click de un plato.
            logicaPlato.click(plato);


        }
    }
    public interface ClickPlato {
        public void click(Producto plato);
    }
}
