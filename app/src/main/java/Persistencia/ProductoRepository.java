package Persistencia;

import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
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

    public List<Producto> getProductosByPosicion(double lat, double lon){
        listaProductos = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                QueryBuilder<Producto, Integer> queryBuilder = this.getDao().queryBuilder();
                Where<Producto, Integer> where = queryBuilder.where();

                where.eq("direccionLatitud", lat).and().eq("direccionLongitud", lon);

                PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();
                listaProductos = this.getDao().query(preparedQuery);
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


     public List<Producto> getProductosValorados(Usuario user) {
        Thread hilo = new Thread(() ->
        {
            try {
            QueryBuilder<Producto, Integer> queryBuilder = this.getDao().queryBuilder();
                Where<Producto, Integer> where = queryBuilder.where();

                where.eq("usuarioPublicador", user).and().gt("valoracion", -1);
                productos = this.getDao().query(queryBuilder.prepare());

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


    public List<Producto> getProductoPorNombre(String nombre) {
        Thread hilo = new Thread(() -> {
            try {
                // Utilizar el método queryBuilder().where().like() para realizar la búsqueda
                QueryBuilder<Producto, Integer> queryBuilder = this.getDao().queryBuilder();
                queryBuilder.where().like("nombre", "%" + nombre + "%");
                listaProductos = queryBuilder.query();

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
        return listaProductos;
    }


    public List<Producto> getReservasEnCurso(Usuario usuario){
        listaProductos = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                QueryBuilder<Producto, Integer> queryBuilder = this.getDao().queryBuilder();
                Where<Producto, Integer> where = queryBuilder.where();

                where.eq("usuarioComprador", usuario.getEmail()).and().eq("entregado", 0);

                PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();
                listaProductos = this.getDao().query(preparedQuery);
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

    public List<Producto> getReservasPrevias(Usuario usuario){
        listaProductos = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                QueryBuilder<Producto, Integer> queryBuilder = this.getDao().queryBuilder();
                Where<Producto, Integer> where = queryBuilder.where();

                where.eq("usuarioComprador", usuario.getEmail()).and().eq("entregado", 1);

                PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();
                listaProductos = this.getDao().query(preparedQuery);
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

    public List<Producto> getProductoPublicadoEnCurso(Usuario usuario){
        listaProductos = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                QueryBuilder<Producto, Integer> queryBuilder = this.getDao().queryBuilder();
                Where<Producto, Integer> where = queryBuilder.where();

                where.eq("usuarioPublicador", usuario.getEmail()).and().eq("entregado", 0);

                PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();
                listaProductos = this.getDao().query(preparedQuery);
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

    public List<Producto> getProductoPublicadoEntregado(Usuario usuario){
        listaProductos = new ArrayList<>();
        Thread hilo = new Thread(() ->
        {
            try {
                QueryBuilder<Producto, Integer> queryBuilder = this.getDao().queryBuilder();
                Where<Producto, Integer> where = queryBuilder.where();

                where.eq("usuarioPublicador", usuario.getEmail()).and().eq("entregado", 1);

                PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();
                listaProductos = this.getDao().query(preparedQuery);
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

    private List<Producto> productos;
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

    public List<Producto> getProductosSinComprador(){
        List<Producto> listaProduct = new ArrayList<>();

        try {
            QueryBuilder<Producto, ?> queryBuilder = getDao().queryBuilder();
            queryBuilder.where().isNull("usuarioComprador");
            PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();

            // Ejecutar la consulta
            listaProduct = getDao().query(preparedQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProduct;
    }
    public List<Producto> getProductosSinVenderPorUser(Usuario usuario){

        Thread hilo = new Thread(() ->
        {

            try {
                QueryBuilder<Producto, ?> queryBuilder = getDao().queryBuilder();
                Where<Producto, ?> where = queryBuilder.where();
                where.eq("usuarioPublicador", usuario).and().isNull("usuarioComprador");
                PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();

                // Ejecutar la consulta
                productos = getDao().query(preparedQuery);


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

    public List<Producto> getPrimerosProductos(){
        listaProductos = new ArrayList<>();

        Thread hilo = new Thread(() ->
        {
            try {
                QueryBuilder<Producto, ?> queryBuilder = getDao().queryBuilder().limit(5L);
                queryBuilder.where().isNull("usuarioComprador");
                PreparedQuery<Producto> preparedQuery = queryBuilder.prepare();

                // Ejecutar la consulta
                listaProductos = getDao().query(preparedQuery);

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
