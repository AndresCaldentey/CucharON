package Persistencia;




import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository extends Repository<Usuario>{

    public UsuarioRepository(ConnectionSource c)  {init(Usuario.class, c);}

    public Usuario getUserByName(String nombre){
        try {
            return this.getDao().queryForEq("nombre", nombre).stream().findFirst().orElse(null);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
