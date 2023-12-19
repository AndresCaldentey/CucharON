package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.R;
import com.example.cucharon.Reserva;
import com.example.cucharon.Usuario;
import com.google.android.gms.maps.model.Circle;

import java.util.List;

import Negocio.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterOpinion extends RecyclerView.Adapter<AdapterOpinion.OpinionHolder> {
    private List<Reserva> reservas;
    Service servicio;
    OnClickListener logicaClickOpinion;
    public AdapterOpinion(List<Reserva> reservas, OnClickListener logicaClickOpinion) {
        this.reservas = reservas;
        servicio = Service.getService();
        this.logicaClickOpinion = logicaClickOpinion;
    }

    @NonNull
    @Override
    public AdapterOpinion.OpinionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterOpinion.OpinionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opinion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOpinion.OpinionHolder holder, int position) {
        if(reservas.get(position).getValoracion() != -1) holder.imprimir(reservas.get(position));
    }

    @Override
    public int getItemCount() {
        if(reservas == null) return 0;
        return reservas.size();
    }

    public class OpinionHolder extends RecyclerView.ViewHolder {
        private CircleImageView fotoPerfil;
        private TextView nombreValoracion,valoracionPlato;
        CardView opinionLayout;
        public OpinionHolder(@NonNull View itemView) {
            super(itemView);
            fotoPerfil = itemView.findViewById(R.id.fotoPerfil);
            nombreValoracion = itemView.findViewById(R.id.nombreValoracion);
            valoracionPlato = itemView.findViewById(R.id.valoracionPerfil);
            opinionLayout = itemView.findViewById(R.id.opinionLayout);
        }
        public void imprimir(Reserva reserva){
            int valoracion = reserva.getValoracion();
            Usuario usuarioCom = reserva.getUsuarioComprador();

            if(usuarioCom.getFoto() != null) fotoPerfil.setImageBitmap(servicio.pasarStringAImagen(usuarioCom.getFoto()));

            nombreValoracion.setText(usuarioCom.getNombre() + " " + usuarioCom.getApellido());

            valoracionPlato.setText(servicio.valoracionAString(valoracion));
            opinionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logicaClickOpinion.click(usuarioCom);
                }
            });
            }

    }
    public interface OnClickListener {
        public void click(Usuario usuario);
    }
}
