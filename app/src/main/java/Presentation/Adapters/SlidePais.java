package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SlidePais extends RecyclerView.Adapter<SlidePais.SliderViewHolder> {
    private List<Pais> paises;
    ClickCategoria logicaBusqueda;

    public SlidePais(List<Pais> paises, ClickCategoria buscar) {
        this.paises = paises;
        this.logicaBusqueda = buscar;

    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_pais_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(paises.get(position));
    }

    @Override
    public int getItemCount() {
        return paises.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView paisImagen;
        private TextView nombreP;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            paisImagen = itemView.findViewById(R.id.paisImagen);
            nombreP = itemView.findViewById(R.id.nombreP);

        }

        public void setImage(Pais pais) {
            paisImagen.setImageResource(pais.getImage());
            nombreP.setText(pais.getNombre());
            paisImagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logicaBusqueda.click(pais);
                }
            });

        }


    }
}
