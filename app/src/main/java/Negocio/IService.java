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
    Usuario getUsuarioByEmail(String correo);
    void crearUsuario(Usuario user);
    void crearProducto(Producto producto);
    Producto getProductoById(int id);
    List<Producto> getProductosByDireccion(String direccion);
    List<Producto> getProductosByPosicion(double lat, double lon);
    public Categoria getCategoriaByName(String nombre);
    List<Producto> getAllProducto();
    public List<Categoria> getAllCategorias();
    void actualizarProducto2(Producto p);
    public Usuario loggedUser = null;
    boolean validTel(int tel);
    boolean validEmail(String email);
    boolean validPassword(String password);
    boolean passwordMatch(String password1, String password2);
    boolean existeEnPlato(String campo);
    boolean validTime(String hora);
    boolean validPrecio(String precio);
    boolean validTimeRange(String hora1, String hora2);
    boolean validRaciones(String raciones);
    void CrearAlerta(String errorString, Context contexto);
    String imagenToString(Bitmap bitmap);
    Bitmap pasarStringAImagen(String img64);
    void setLoggedUser(Usuario user);
    Usuario getLoggedUser();
    void clearLoggedUser() ;
    UsuarioRepository getUserRepo();
    void guardarProductoCategoria(ProductoCategoria productoCategoria);
    List<ProductoCategoria> getAllProductoCategoria();
    List<Producto> getProductosPubPorUser(Usuario user);
    List<Producto> getProductosSinComprar();
    void actualizarUser(Usuario user);
}
