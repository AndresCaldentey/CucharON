package Persistencia;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository extends Repository<Usuario>{
    private Usuario usuario;

    public UsuarioRepository(ConnectionSource c)  {init(Usuario.class, c);}

    public Usuario getUserByName(String nombre){
        try {
            List<Usuario> usuarios = this.getDao().queryForEq("nombre", nombre);
            if (!usuarios.isEmpty()) {
                // Si se encuentra al menos un usuario con el nombre especificado, retornamos el primero
                return usuarios.get(0);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario getUserByEmail(String email){
        usuario = null;
        Thread hilo = new Thread(() ->
        {
            try {
                List<Usuario> usuarios = this.getDao().queryForEq("email", email);
                if (!usuarios.isEmpty()) {
                    // Si se encuentra al menos un usuario con el nombre especificado, retornamos el primero
                    usuario = usuarios.get(0);
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
        return usuario;

        /*try {
            List<Usuario> usuarios = this.getDao().queryForEq("email", email);
            if (!usuarios.isEmpty()) {
                // Si se encuentra al menos un usuario con el nombre especificado, retornamos el primero
                return usuarios.get(0);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }*/
    }


}
