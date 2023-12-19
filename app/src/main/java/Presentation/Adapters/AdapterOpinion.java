package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.R;
import com.example.cucharon.Reserva;

import java.util.List;

public class AdapterOpinion extends RecyclerView.Adapter<AdapterOpinion.OpinionHolder> {
    private List<Reserva> reservas;

    public AdapterOpinion(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public AdapterOpinion.OpinionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterOpinion.OpinionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opinion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOpinion.OpinionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(reservas == null) return 0;
        return reservas.size();
    }

    public class OpinionHolder extends RecyclerView.ViewHolder {

        public OpinionHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
