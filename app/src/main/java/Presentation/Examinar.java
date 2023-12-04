package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cucharon.R;

import java.util.ArrayList;
import java.util.List;

import Presentation.Adapters.Area;
import Presentation.Adapters.Pais;
import Presentation.Adapters.SlidePais;

public class Examinar extends Fragment {
    private ViewPager2 slidePaises;

    public Examinar() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slidePaises = view.findViewById(R.id.slidePaises);
        List<Pais> paises = new ArrayList<>();
        List<Area> areas = new ArrayList<>();
        paises.add(new Pais(R.drawable.platochina, "China"));
        paises.add(new Pais(R.drawable.platoindia, "India"));
        paises.add(new Pais(R.drawable.platojapon, "Japón"));
        areas.add(new Area("Asia",false));



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.examinar_f, container, false);

    }
}