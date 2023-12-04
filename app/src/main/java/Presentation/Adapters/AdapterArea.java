package Presentation.Adapters;

import android.app.Activity;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterArea extends RecyclerView.Adapter<AdapterArea.AdapterViewHolder> {
    List<Area> areas;
    List<Pais> paises;
    private Activity activity;

    public AdapterArea(Activity activity, List<Area> areas, List<Pais> paises) {
        this.areas = areas;
        this.paises = paises;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterArea.AdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.examinar_f, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
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
            slidePaises.setAdapter(new SlidePais(paises));
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
                    page.setScaleY(0.95f + r * 0.05f);
                }
            });

            slidePaises.setPageTransformer(compositePageTransformer);
            slidePaises.setCurrentItem(2);
            desplegable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    area.setVisible(!area.isVisible());
                }
            });
            if(area.visible) {
                slidePaises.setVisibility(View.VISIBLE);
                flecha.setRotation(0);
            } else {
                slidePaises.setVisibility(View.GONE);
                flecha.setRotation(180);
            }
        }


    }
}