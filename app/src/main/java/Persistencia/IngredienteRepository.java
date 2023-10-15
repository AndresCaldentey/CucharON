package Persistencia;

import com.example.cucharon.Ingrediente;
import com.j256.ormlite.support.ConnectionSource;

public class IngredienteRepository extends Repository<Ingrediente> {
    public IngredienteRepository(ConnectionSource c)  {init(Ingrediente.class, c);}
}
