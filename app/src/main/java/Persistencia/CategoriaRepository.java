package Persistencia;

import com.example.cucharon.Categoria;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class CategoriaRepository extends Repository<Categoria>{
    public CategoriaRepository(ConnectionSource c)  {init(Categoria.class, c);}

}
