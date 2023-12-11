package Presentation.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cucharon.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPaisNuevoPlato extends RecyclerView.Adapter<AdapterPaisNuevoPlato.PaisSliderHolder> {
    List<Pais> paises;
    ClickCategoria logicaBusqueda;
    private int selectedItemPosition = RecyclerView.NO_POSITION;

    public AdapterPaisNuevoPlato(List<Pais> paises, ClickCategoria buscar) {
        this.paises = paises;
        this.logicaBusqueda = buscar;

    }

    @NonNull
    @Override
    public AdapterPaisNuevoPlato.PaisSliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterPaisNuevoPlato.PaisSliderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_pais_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPaisNuevoPlato.PaisSliderHolder holder, int position) {
        holder.setImage(paises.get(position));
        holder.itemView.setSelected(selectedItemPosition == position);
    }

    @Override
    public int getItemCount() {
        return paises.size();
    }

    public class PaisSliderHolder extends RecyclerView.ViewHolder {

        private CircleImageView paisImagen;
        private TextView nombreP;
        private LinearLayout layoutPais;
        Drawable seleccionado;

        public PaisSliderHolder(@NonNull View itemView) {
            super(itemView);
            paisImagen = itemView.findViewById(R.id.paisImagen);
            nombreP = itemView.findViewById(R.id.nombreP);
            layoutPais = itemView.findViewById(R.id.layoutPais);
            seleccionado = itemView.getResources().getDrawable(R.drawable.boton_verde_borde);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int previousSelectedItem = selectedItemPosition;
                    selectedItemPosition = getAdapterPosition();
                    notifyItemChanged(previousSelectedItem);
                    notifyItemChanged(selectedItemPosition);
                    logicaBusqueda.click(paises.get(getAdapterPosition()));
                }
            });

        }

        public void setImage(Pais pais) {
            paisImagen.setImageResource(pais.getImage());
            nombreP.setText(pais.getNombre());

        }
    }
}
