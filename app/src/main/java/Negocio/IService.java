package Negocio;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.cucharon.Categoria;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.Usuario;

import java.util.List;

import Persistencia.UsuarioRepository;

public interface IService {
    static final int SELECT_IMAGE = 1;

    /*PERSISTENCIA USUARIO*/
    void crearUsuario(Usuario user);
    Usuario getUsuarioByEmail(String correo);
    void actualizarUser(Usuario user);

    /*PERSISTENCIA PRODUCTO*/
    void crearProducto(Producto producto);
    Producto getProductoById(int id);
    List<Producto> getProductosByDireccion(String direccion);
    List<Producto> getProductosByPosicion(double lat, double lon);
    List<Producto> getProductosPubPorUser(Usuario usuario);
    List<Producto> getProductosSinComprar();
    List<Producto> getAllProducto();
    void actualizarProducto(Producto p);

    /*PERSISTENCIA CATEGORIA*/
    Categoria getCategoriaByName(String nombre);
    List<Categoria> getAllCategorias();

    /*PERSISTENCIA PRODUCTO-CATEGORIA*/
    void guardarProductoCategoria(ProductoCategoria productoCategoria);
    List<ProductoCategoria> getAllProductoCategoria();

    /*GESTION DE SESIONES*/
    public Usuario loggedUser = null;
    void setLoggedUser(Usuario user);
    Usuario getLoggedUser();
    void clearLoggedUser() ;

    /*VALIDACIONES*/
    boolean validTel(int tel);
    boolean validEmail(String email);
    boolean validPassword(String password);
    boolean passwordMatch(String password1, String password2);
    boolean existeEnPlato(String campo);
    boolean validTime(String hora);
    boolean validPrecio(String precio);
    boolean validTimeRange(String hora1, String hora2);
    boolean validRaciones(String raciones);

    /*GESTION DE MENSAJES*/
    void CrearAlerta(String errorString, Context contexto);

    /*GESTION DE IMAGENES*/
    String imagenToString(Bitmap bitmap);
    Bitmap pasarStringAImagen(String img64);
}
