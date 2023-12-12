package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SliderSabor extends RecyclerView.Adapter<SliderSabor.SliderSaborHolder>{
    List<Sabor> sabores;
    LogicaSabor logicaSabor;
    public SliderSabor (List<Sabor> sabores) { this.sabores = sabores; }
    @NonNull
    @Override
    public SliderSabor.SliderSaborHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderSaborHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsabor_add_producto, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderSabor.SliderSaborHolder holder, int position) {
        holder.imprimir(sabores.get(position));
    }

    @Override
    public int getItemCount() {
        return sabores.size();
    }

    public class SliderSaborHolder extends RecyclerView.ViewHolder {
        CircleImageView imagenSabor;
        TextView tituloSabor;
        ImageView fraseSabor;
        public SliderSaborHolder(@NonNull View itemView) {
            super(itemView);
            imagenSabor = itemView.findViewById(R.id.imagenSabor);
            tituloSabor = itemView.findViewById(R.id.tituloSabor);
            fraseSabor = itemView.findViewById(R.id.fraseSabor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logicaSabor.click(sabores.get(getAdapterPosition()));
                }
            });
        }

        public void imprimir(Sabor sabor) {
            imagenSabor.setImageResource(sabor.getImagenSabor());
            tituloSabor.setText(sabor.getTituloSabor());
            fraseSabor.setImageResource(sabor.getFraseSabor());
        }
    }
    public interface LogicaSabor{
        public void click(Sabor sabor);
    }
}
