package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class ProductoRepository extends Repository<Producto>{

    public ProductoRepository(ConnectionSource c)  {init(Producto.class, c);}
    List<Producto> productos;
    public List<Producto> getProductosPorUsuario(Usuario user){

        Thread hilo = new Thread(() ->
        {
            try {
                productos = this.getDao().queryForEq("usuarioPublicador", user);

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
        return productos;
    }
}
