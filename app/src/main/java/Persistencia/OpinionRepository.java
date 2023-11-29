package Persistencia;

import com.example.cucharon.Opinion;
import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpinionRepository extends Repository<Opinion>{
    private  Opinion opinion;
    private List<Opinion> listaOpiniones;
    public OpinionRepository(ConnectionSource c)  {init(Opinion.class, c);}

    public List<Opinion> getOpinionByUsuarioEvaluado(Usuario usuario){
        listaOpiniones = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                listaOpiniones = this.getDao().queryForEq("usuarioEvaluado", usuario.getEmail());
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
        return listaOpiniones;
    }
}
