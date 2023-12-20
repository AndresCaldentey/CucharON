package Persistencia;

import com.example.cucharon.Categoria;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository extends Repository<Categoria>{
    private Categoria categoria;

    public CategoriaRepository(ConnectionSource c)  {init(Categoria.class, c);}

    public Categoria getCategoriaByName(String nombre){
        categoria = null;
        Thread hilo = new Thread(() ->
        {
            try {
              List<Categoria> categorias = this.getDao().queryForEq("nombre", nombre);
                if (!categorias.isEmpty()) {
                    // Si se encuentra al menos un usuario con el nombre especificado, retornamos el primero
                    categoria = categorias.get(0);
                }
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
        return categoria;


    }

}
