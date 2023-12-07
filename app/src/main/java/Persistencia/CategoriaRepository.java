package Persistencia;

import com.example.cucharon.Categoria;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class CategoriaRepository extends Repository<Categoria>{
    public CategoriaRepository(ConnectionSource c)  {init(Categoria.class, c);}
    public Categoria getCategoriaByName(String nombre){
        try {
            List<Categoria> categorias = this.getDao().queryForEq("nombre", nombre);
            if (!categorias.isEmpty()) {
                // Si se encuentra al menos un usuario con el nombre especificado, retornamos el primero
                return categorias.get(0);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
