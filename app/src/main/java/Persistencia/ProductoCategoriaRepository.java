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

    public ProductoCategoriaRepository(ConnectionSource c)  {
        init(ProductoCategoria.class, c);
        conn = c;
    }

    public List<Producto> BuscarPorCategoria(String nombreCategoria) {
        final List<Producto> productos = new ArrayList<>();

        Thread hilo = new Thread(() -> {
            CategoriaRepository categoriaRepository = new CategoriaRepository(conn);
            Categoria busqueda = categoriaRepository.getCategoriaByName(nombreCategoria);
            try {
                QueryBuilder<ProductoCategoria, ?> queryBuilder = getDao().queryBuilder();
                queryBuilder.where().eq("categoria", busqueda);
                PreparedQuery<ProductoCategoria> preparedQuery = queryBuilder.prepare();

                // Ejecutar la consulta
                List<ProductoCategoria> productoCategorias = getDao().query(preparedQuery);

                // Obtener los productos de las ProductoCategoria encontradas
                for (ProductoCategoria pc : productoCategorias) {
                    productos.add(pc.getProducto());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        hilo.start();

        // Esperar al hilo
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return productos;
    }





}
