package Negocio;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.cucharon.Categoria;
import com.example.cucharon.Producto;
import com.example.cucharon.Usuario;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Persistencia.CategoriaRepository;
import Persistencia.ProductoRepository;
import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;
public class Service implements IService{
    private UsuarioRepository userRepo;
    private ProductoRepository productoRepo;
    private CategoriaRepository categoriaRepo;
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
        categoriaRepo = new CategoriaRepository(SingletonConnection.getSingletonInstance());
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
    public Categoria getCategoriaByName(String nombre) { return categoriaRepo.getCategoriaByName(nombre); }
    public List<Producto> getAllProducto() { return productoRepo.obtenerTodos(); }
    public List<Categoria> getAllCategorias() {return categoriaRepo.obtenerTodos();}
    public void actualizarProducto2(Producto p) { productoRepo.actualizar(p);}


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

    public boolean existeEnPlato(String campo){
        return campo != "";
    }
    public boolean validTime(String hora){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false); // No permitir valores inválidos (por ejemplo, 25:70 sería inválido)

        try {
            // Intentar parsear la cadena a un objeto Date
            Date parsedDate = sdf.parse(hora);
            return true; // Si no se lanza una excepción, la hora es válida
        } catch (ParseException e) {
            return false; // Si se lanza una excepción, la hora es inválida
        }
    }
    public boolean validPrecio(String precio){
        try {
            double parsedPrecio = Double.parseDouble(precio);
            return parsedPrecio > 0; // El precio debe ser un número positivo
        } catch (NumberFormatException e) {
            return false; // Si hay una excepción, la cadena no representa un número válido
        }
    }
    public boolean validTimeRange(String hora1, String hora2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sdf.setLenient(false);

            Date parsedHora1 = sdf.parse(hora1);
            Date parsedHora2 = sdf.parse(hora2);

            // Verificar que hora1 sea menor que hora2
            return parsedHora1.before(parsedHora2);
        } catch (ParseException e) {
            return false; // Si hay una excepción, las horas no están en el formato esperado
        }
    }
    public boolean validRaciones(String raciones){
        try {
            int racionesInt = Integer.parseInt(raciones);
            return racionesInt >= 0; // El número de raciones debe ser un entero no negativo
        } catch (NumberFormatException e) {
            return false; // Si hay una excepción, la cadena no representa un número entero válido
        }
    }

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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public Bitmap pasarStringAImagen(String img64){
        byte[] imageBytes = Base64.decode(img64, Base64.DEFAULT);
        Bitmap imageResult = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return imageResult;
    }

}
