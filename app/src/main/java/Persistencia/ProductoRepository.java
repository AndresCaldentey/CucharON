package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

public class ProductoRepository extends Repository<Producto>{

    public ProductoRepository(ConnectionSource c)  {init(Producto.class, c);}
}
