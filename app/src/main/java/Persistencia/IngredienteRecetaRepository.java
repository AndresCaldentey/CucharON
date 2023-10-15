package Persistencia;


import com.example.cucharon.IngredienteReceta;
import com.j256.ormlite.support.ConnectionSource;

public class IngredienteRecetaRepository  extends Repository<IngredienteReceta> {
    public IngredienteRecetaRepository(ConnectionSource c)  {init(IngredienteReceta.class, c);}
}
