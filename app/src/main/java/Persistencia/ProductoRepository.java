package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository extends Repository<Producto>{
    private  Producto producto;
    private List<Producto> listaProductos;
    public ProductoRepository(ConnectionSource c)  {init(Producto.class, c);}

    public List<Producto> getProductosByDireccion(String direccion){
        listaProductos = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                listaProductos = this.getDao().queryForEq("direccionRecogida", direccion);
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
        return listaProductos;
    }
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
