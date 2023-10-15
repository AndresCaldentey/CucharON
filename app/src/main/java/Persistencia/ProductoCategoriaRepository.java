package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.j256.ormlite.support.ConnectionSource;

public class ProductoCategoriaRepository extends Repository<ProductoCategoria>{

    public ProductoCategoriaRepository(ConnectionSource c)  {init(ProductoCategoria.class, c);}

}
