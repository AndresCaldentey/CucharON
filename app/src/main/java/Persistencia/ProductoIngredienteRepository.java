package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.ProductoIngrediente;
import com.j256.ormlite.support.ConnectionSource;

public class ProductoIngredienteRepository extends Repository<ProductoIngrediente>{

    public ProductoIngredienteRepository(ConnectionSource c)  {init(ProductoIngrediente.class, c);}
}
