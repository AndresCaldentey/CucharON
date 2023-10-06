package Persistencia;




import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

public class UsuarioRepository extends Repository<Usuario>{

    public UsuarioRepository(ConnectionSource c)  {init(Usuario.class, c);}
}
