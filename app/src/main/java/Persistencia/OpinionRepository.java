package Persistencia;

import com.example.cucharon.Opinion;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

public class OpinionRepository extends Repository<Opinion>{

    public OpinionRepository(ConnectionSource c)  {init(Opinion.class, c);}
}
