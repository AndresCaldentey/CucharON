package Negocio;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;

import java.util.List;

import com.example.cucharon.Usuario;

import Persistencia.UsuarioRepository;

public interface IService {
    Usuario getUsuarioByEmail(String correo);
    void crearUsuario(Usuario user);
    void crearProducto(Producto producto);
    Producto getProductoById(int id);
    List<Producto> getAllProducto();
    void actualizarProducto2(Producto p);
    public Usuario loggedUser = null;
    boolean validTel(int tel);
    boolean validEmail(String email);
    boolean validPassword(String password);
    boolean passwordMatch(String password1, String password2);
    boolean existeEnPlato(String campo);
    boolean validTime(String hora);
    boolean validPrecio(String precio);
    boolean validTimeRange();
    void ErrorAlert(String errorString, Context contexto);
    String imagenToString(Bitmap bitmap);
    Bitmap pasarStringAImagen(String img64);
    void setLoggedUser(Usuario user);
    Usuario getLoggedUser();
    UsuarioRepository getUserRepo();
}
