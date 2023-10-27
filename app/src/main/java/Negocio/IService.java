package Negocio;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;

public interface IService {
    Usuario getUsuarioByEmail(String correo);
    void crearUsuario(Usuario user);
    void crearProducto(Producto producto);
    Producto getProductoById(int id);
    void actualizarProducto(Producto p);
    boolean validTel(int tel);
    boolean validEmail(String email);
    boolean validPassword(String password);
    boolean passwordMatch(String password1, String password2);
    void ErrorAlert(String errorString, Context contexto);
    String imagenToString(Bitmap bitmap);
    Bitmap pasarStringAImagen(String img64);
}
