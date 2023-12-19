package Presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.util.List;

import Negocio.IService;
import Negocio.Service;
import Presentation.Adapters.AdaptadorPlato;

public class VerBusqueda extends Fragment {
    private IService service;
    private TextView textViewPais;
    private RecyclerView recyclerPlatos;

    public VerBusqueda() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = Service.getService();
        recyclerPlatos = view.findViewById(R.id.recylerPlatosVerBusqueda);
        textViewPais = view.findViewById(R.id.paisVerBusqueda);
        ImageView cerrar = view.findViewById(R.id.cerrarVerBusqueda);

        cerrar.setOnClickListener((view1) -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, new Examinar()).commit();
            if(getActivity() != null) {
                if(getActivity() instanceof Navegacion) {
                    Navegacion activityActual = (Navegacion) getActivity();
                    activityActual.showPerfil();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("datos", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String categoria = result.getString("categoria");
                textViewPais.setText(categoria+ " en tu");

                List<Producto> platos = service.buscarPorCategoria(categoria);
                AdaptadorPlato.OnClickPerfilListener logicaClPerfil = new AdaptadorPlato.OnClickPerfilListener() {
                    @Override
                    public void click(Usuario usuario) {
                        Intent intent = new Intent(getContext(), Perfil.class);
                        intent.putExtra("usuario", usuario.getEmail());
                        startActivity(intent);
                    }
                };
                AdaptadorPlato platosAdapter = new AdaptadorPlato(platos, getActivity(), logicaClPerfil);
                recyclerPlatos.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerPlatos.setAdapter(platosAdapter);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_busqueda, container, false);
    }

}