package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.Receta;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecetaRepository extends Repository<Receta>{
    private  Receta receta;
    private List<Receta> listaRecetas;
    public RecetaRepository(ConnectionSource c)  {init(Receta.class, c);}

    public List<Receta> getRecetaByChef(Usuario usuario) {
        listaRecetas = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                listaRecetas = this.getDao().queryForEq("usuario_chef", usuario);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        hilo.start();

        //Esperar al hilo
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listaRecetas;
    }
}
