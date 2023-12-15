package Persistencia;

import com.example.cucharon.Categoria;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoCategoriaRepository extends Repository<ProductoCategoria>{
    ConnectionSource conn;
    List<ProductoCategoria> listaProductos;

    public ProductoCategoriaRepository(ConnectionSource c)  {
        init(ProductoCategoria.class, c);
        conn = c;
    }

    public List<ProductoCategoria> BuscarPorCategoria(String categoria) {


        Thread hilo = new Thread(() ->
        {
            try {
                listaProductos = this.getDao().queryForEq("categoria", categoria);
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





}
