package Presentation;

import android.content.Context;
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

import Presentation.Adapters.DataObject;
import Presentation.Adapters.OnDataPassListener;
import Presentation.Adapters.Sabor;
import Presentation.Adapters.SliderSabor;


public class AddPlatoSabores extends Fragment {

    private OnDataPassListener dataPassListener;
    List<Sabor> sabores = new ArrayList<>();
    String tituloSabor;
    private ViewPager2 slideSabores;
    public AddPlatoSabores() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SliderSabor.LogicaSabor logicaSabor = new SliderSabor.LogicaSabor() {
            @Override
            public void click(Sabor sabor) {

                tituloSabor = sabor.getTituloSabor();
                sendSaboresToActivity(tituloSabor);
                getParentFragmentManager().beginTransaction().replace(R.id.addPlatoFragmentMan, new Add_detalles()).commit();
            }
        };
        slideSabores = view.findViewById(R.id.slideSabores);
        inicializarSabores();
        slideSabores.setAdapter(new SliderSabor(sabores, logicaSabor));

        slideSabores.setClipToPadding(false);
        slideSabores.setClipChildren(false);
        slideSabores.setOffscreenPageLimit(3);
        slideSabores.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        slideSabores.setPageTransformer(compositePageTransformer);
        slideSabores.setCurrentItem(1);



    }

    private void inicializarSabores() {
        Sabor saborAux = new Sabor(R.drawable.salado_sabor, "Salado", R.drawable.sabor_salado_frase);
        sabores.add(saborAux);
        saborAux = new Sabor(R.drawable.dulce_sabor, "Dulce", R.drawable.dulce_sabor_frase);
        sabores.add(saborAux);
        saborAux = new Sabor(R.drawable.amargo_sabor, "Amargo", R.drawable.sabor_amargo_frase);
        sabores.add(saborAux);
        saborAux = new Sabor(R.drawable.acido_sabor, "Ácido", R.drawable.sabor_acido_frase);
        sabores.add(saborAux);
        saborAux = new Sabor(R.drawable.umami_sabor, "Umami", R.drawable.sabor_umami_frase);
        sabores.add(saborAux);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plato_sabores, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataPassListener = (OnDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar OnDataPassListener");
        }
    }

    private void sendSaboresToActivity(String data) {
        DataObject dataObject = new DataObject("categoria", data);
        dataPassListener.onDataPass(dataObject);
    }
}