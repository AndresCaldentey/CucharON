package Negocio;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;

import java.io.ByteArrayOutputStream;
import java.util.List;

import Persistencia.ProductoRepository;
import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;
public class Service implements IService{
    private UsuarioRepository userRepo;
    private ProductoRepository productoRepo;
    private static Service instancia;
    private Usuario loggedUser;
    public UsuarioRepository getUserRepo() { return userRepo; }
    public void setLoggedUser(Usuario user) {
        if(loggedUser == null) loggedUser = user;
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public Service() {
        userRepo = new UsuarioRepository(SingletonConnection.getSingletonInstance());
        productoRepo = new ProductoRepository(SingletonConnection.getSingletonInstance());
    }
    public static Service getService() {
        if(instancia == null) instancia = new Service();
        return instancia;
    }

    //PERSISTENCIA
    public Usuario getUsuarioByEmail(String correo) { return userRepo.getUserByEmail(correo); }
    public void crearUsuario(Usuario user) { userRepo.guardar(user); }
    public void crearProducto(Producto producto) { productoRepo.guardar(producto); }
    public Producto getProductoById(int id) { return productoRepo.obtener(id); }
    public List<Producto> getAllProducto() { return productoRepo.obtenerTodos(); }
    public void actualizarProducto(Producto producto) { productoRepo.actualizar(producto); }


    //OTRAS COSAS
    public  boolean validTel(int tel) {
        String numeroComoString = Integer.toString(tel);
        int cantidadDigitos = numeroComoString.length();
        return cantidadDigitos == 9;
    }

    public  boolean validEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            int posicionArroba = email.indexOf("@");
            int posicionPunto = email.lastIndexOf(".");

            // Asegurarse de que haya al menos un carácter alrededor del "@" y ".".
            if (posicionArroba < posicionPunto - 1 && posicionPunto < email.length() - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean validPassword(String password) {
        return password.matches(".*\\d.*") && password.chars().anyMatch(Character::isUpperCase) &&
                password.length() >= 8;
    }

    public boolean passwordMatch(String password1, String password2) { return password1.equals(password2); }
    public void ErrorAlert(String errorString, Context contexto) {
        AlertDialog.Builder alert = new AlertDialog.Builder(contexto);
        alert.setMessage(errorString)
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public String imagenToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public Bitmap pasarStringAImagen(String img64){
        byte[] imageBytes = Base64.decode(img64, Base64.DEFAULT);
        Bitmap imageResult = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return imageResult;
    }

}
