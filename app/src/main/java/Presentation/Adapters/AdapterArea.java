package Presentation.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cucharon.R;

import java.util.List;

public class AdapterArea extends RecyclerView.Adapter<AdapterArea.AdapterViewHolder> {
    List<Area> areas;
    List<Pais> paises;
    ClickCategoria logicaBusqueda;
    public AdapterArea(List<Area> areas, List<Pais> paises, ClickCategoria buscar) {
        this.areas = areas;
        this.paises = paises;
        this.logicaBusqueda = buscar;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterArea.AdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.desplegable_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.funcionalidadExpandir(areas.get(position));
        holder.imprimir(areas.get(position));


    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView nombreArea;
        ViewPager2 slidePaises;
        CardView desplegable;
        ImageView flecha;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreArea = itemView.findViewById(R.id.nombreArea);
            slidePaises = itemView.findViewById(R.id.slidePaises);
            desplegable = itemView.findViewById(R.id.desplegable);
            flecha = itemView.findViewById(R.id.flecha);

        }
        public void imprimir(Area area) {
            nombreArea.setText(area.getNombreArea());
            slidePaises.setAdapter(new SlidePais(area.getPaises(), logicaBusqueda));
            slidePaises.setClipToPadding(false);
            slidePaises.setClipChildren(false);
            slidePaises.setOffscreenPageLimit(3);
            slidePaises.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(40));
            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    float r = 1 - Math.abs(position);
                    page.setScaleY(0.85f + r * 0.15f);
                    page.setScaleX(0.85f + r * 0.15f);
                }
            });

            slidePaises.setPageTransformer(compositePageTransformer);
            slidePaises.setCurrentItem(1);

            if(area.visible) {
                slidePaises.setVisibility(View.VISIBLE);
                flecha.setRotation(0);
                
            } else {
                slidePaises.setVisibility(View.GONE);
                flecha.setRotation(180);

            }
        }
        public void funcionalidadExpandir(Area area){
            desplegable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    area.setVisible(!area.isVisible());
                    notifyItemChanged(getLayoutPosition());
                }
            });
        }


    }
}