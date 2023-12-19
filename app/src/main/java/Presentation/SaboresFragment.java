package Presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cucharon.R;


public class SaboresFragment extends Fragment {

    OnClickListener clickSabor;

    public SaboresFragment(OnClickListener clickSabor) {
        this.clickSabor = clickSabor;
    }

    public SaboresFragment() {
        // Required empty public constructor
    }
    CardView dulceSabor, amargoSabor, acidoSabor, umamiSabor, saladoSabor;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dulceSabor = view.findViewById(R.id.dulceSabor);
        amargoSabor = view.findViewById(R.id.amargoSabor);
        acidoSabor = view.findViewById(R.id.acidoSabor);
        umamiSabor = view.findViewById(R.id.umamiSabor);
        saladoSabor = view.findViewById(R.id.saladoSabor);

        dulceSabor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSabor.click("Dulce");
            }
        });
        amargoSabor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSabor.click("Amargo");
            }
        });
        acidoSabor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSabor.click("Ácido");
            }
        });
        umamiSabor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSabor.click("Umami");
            }
        });
        saladoSabor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSabor.click("Salado");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sabores, container, false);
    }

    public interface OnClickListener {
        public void click(String sabor);
    }
}