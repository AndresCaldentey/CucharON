package Negocio;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.cucharon.Categoria;
import com.example.cucharon.Opinion;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.Receta;
import com.example.cucharon.Usuario;

import java.util.List;

import Persistencia.UsuarioRepository;

public interface IService {
    static final int SELECT_IMAGE = 1;

    /*PERSISTENCIA USUARIO*/
    void crearUsuario(Usuario user);
    Usuario getUsuarioByEmail(String correo);
    void actualizarUser(Usuario user);
    boolean cancelarReserva(Producto producto, Usuario user);


    /*PERSISTENCIA PRODUCTO*/
    Producto crearProducto(Producto producto);
    Producto getProductoById(int id);
    List<Producto> getPrimerosProductos();
    List<Producto> getProductosByPosicion(double lat, double lon);
    List<Producto> getProductosPubPorUser(Usuario usuario);
    List<Producto> getProductosSinComprar();
    List<Producto> getProductoReservadoEnCurso();
    List<Producto> getProductoReservadoEntregado();
    List<Producto> getProductoPublicadoEnCurso();
    List<Producto> getProductoPublicadoEntregado();
    List<Producto> getAllProducto();
    void actualizarProducto(Producto p);
    void setValoracionProducto(Producto producto, int valoracion);
    void borrarProducto(Producto producto);
    List<Producto> getProductosSinVenderPorUser(Usuario user);
    List<Producto> getProductoPorNombre(String nombre);
    List<Producto> getProductosValorados(Usuario user);

    /*PERSISTENCIA CATEGORIA*/
    Categoria getCategoriaByName(String nombre);
    List<Categoria> getAllCategorias();

    /*PERSISTENCIA PRODUCTO-CATEGORIA*/
    List<Categoria> getCategoriasPorProducto(Producto producto);
    void guardarProductoCategoria(ProductoCategoria productoCategoria);
    List<ProductoCategoria> getAllProductoCategoria();
    List<Producto> buscarPorCategoria(String categoria);

    /*PERSISTENCIA OPINIONES*/
    void crearOpinion(Opinion opinion);
    Opinion getOpinionById(int id);
    List<Opinion> getOpinionByUsuarioEvaluado(Usuario usuario);
    List<Opinion> getOpinionByEvaluador(Usuario usuario);
    void actualizarOpinion(Opinion opinion);

    /*PERSISTENCIA RECETAS*/
    void crearReceta(Receta receta);
    Receta getRecetaById(int id);
    List<Receta> getRecetaByChef(Usuario usuario);
    void actualizarReceta(Receta receta);
    void borrarReceta(Receta receta);

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
    boolean isValidDate(String inputDate);

    /*GESTION DE MENSAJES*/
    void CrearAlerta(String errorString, Context contexto);

    /*GESTION DE IMAGENES*/
    String imagenToString(Bitmap bitmap);
    Bitmap pasarStringAImagen(String img64);

    String valoracionAString(int valoracion);
    void pulsarPerfil(Context context, Usuario user);
}
