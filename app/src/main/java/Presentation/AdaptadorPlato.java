package Presentation;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.List;

//El Adaptador es donde se almacena cada instancia de mostrar un plato y el holder es como se define cada instancia de mostrar plato.
public class AdaptadorPlato extends RecyclerView.Adapter<AdaptadorPlato.AdaptadorPlatoHolder>{

    //Aquí guardamos la lista de los platos que queremos mostrar
    List<Producto> platos = new ArrayList<>();
    Context context;
    Class clase;

    public AdaptadorPlato(Class clase) {
        this.clase = clase;
    }
    public AdaptadorPlato(List<Producto> platos) {
        this.platos = platos;
    }
    public void setPlatos (List<Producto> platos) { this.platos = platos; }
    @NonNull
    @Override
    public AdaptadorPlato.AdaptadorPlatoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Este método es obligatorio por el extends y permite la relación entre el holder(cada item por separado) y la vista recyclerView
        this.context = parent.getContext();
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

    class AdaptadorPlatoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //inicializar las variables :)
        TextView nomP, recP, precioP;
        public AdaptadorPlatoHolder(@NonNull View itemView) {
            super(itemView);
            nomP = itemView.findViewById(R.id.nomP);
            recP = itemView.findViewById(R.id.recP);
            precioP = itemView.findViewById(R.id.precioP);
            itemView.setOnClickListener(this);
        }
        public void imprimir(int position) {
            //Este método se encarga de imprimir el plato
            nomP.setText(platos.get(position).getNombre());
            recP.setText(platos.get(position).getHoraRecogida());
            precioP.setText("" + platos.get(position).getPrecio());
        }

        @Override
        public void onClick(View view) {
            //aquí se define el listener que espera al click de un plato.
            IUreserva reserva = new IUreserva();
            Intent intent = new Intent(context,clase);
            intent.putExtra("Producto",platos.get(getLayoutPosition()).getIdProducto());
            context.startActivity(intent);

        }
    }
}
