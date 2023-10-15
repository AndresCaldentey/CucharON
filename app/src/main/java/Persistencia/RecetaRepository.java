package Persistencia;

import com.example.cucharon.Receta;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

public class RecetaRepository extends Repository<Receta>{

    public RecetaRepository(ConnectionSource c)  {init(Receta.class, c);}

}
