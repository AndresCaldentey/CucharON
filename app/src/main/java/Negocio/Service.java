package Negocio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.cucharon.Categoria;
import com.example.cucharon.Opinion;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.Receta;
import com.example.cucharon.Usuario;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Persistencia.CategoriaRepository;
import Persistencia.OpinionRepository;
import Persistencia.ProductoCategoriaRepository;
import Persistencia.ProductoRepository;
import Persistencia.RecetaRepository;
import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;
import Presentation.Perfil;

public class Service implements IService{
    private final UsuarioRepository userRepo;
    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;
    private final ProductoCategoriaRepository productoCategoriaRepo;
    private final OpinionRepository opinionRepo;
    private final RecetaRepository recetaRepo;
    private static Service instancia;
    private Usuario loggedUser;

    public Service() {
        userRepo = new UsuarioRepository(SingletonConnection.getSingletonInstance());
        productoRepo = new ProductoRepository(SingletonConnection.getSingletonInstance());
        categoriaRepo = new CategoriaRepository(SingletonConnection.getSingletonInstance());
        productoCategoriaRepo = new ProductoCategoriaRepository(SingletonConnection.getSingletonInstance());
        opinionRepo = new OpinionRepository(SingletonConnection.getSingletonInstance());
        recetaRepo = new RecetaRepository(SingletonConnection.getSingletonInstance());
    }
    public static Service getService() {
        if(instancia == null) instancia = new Service();
        return instancia;
    }

    /*GESTION DE SESIONES*/
    public void setLoggedUser(Usuario user) {
         loggedUser = user;
    }
    public Usuario getLoggedUser() {
        return loggedUser;
    }
    public void clearLoggedUser() { loggedUser = null; }

    /*PERSISTENCIA USUARIO*/
    public void crearUsuario(Usuario user) { userRepo.guardar2(user); }
    public Usuario getUsuarioByEmail(String correo) { return userRepo.getUserByEmail(correo); }
    public void actualizarUser(Usuario user) { userRepo.actualizar(user);}
    public boolean cancelarReserva(Producto producto, Usuario user) {
        if(producto.getUsuarioComprador() == user) {
            producto.setUsuarioComprador(null);
            productoRepo.actualizar(producto);
            return true;
        }
        else {
            return false;
        }
    }

    /*PERSISTENCIA PRODUCTO*/
    public void setValoracionProducto(Producto producto, int valoracion) {
        Usuario userPub = producto.getUsuarioPublicador();
        producto.setValoracion(valoracion);
        userPub.setCantidadValoracion(userPub.getCantidadValoracion() + valoracion);
        List<Producto> productosValorados = productoRepo.getProductosValorados(userPub);
        userPub.setValoracion( userPub.getCantidadValoracion() / (productosValorados.size() + 1) );
        productoRepo.actualizar(producto);
        userRepo.actualizar(userPub);
    }
    public Producto crearProducto(Producto producto) { productoRepo.guardar2(producto);
                                                        return producto;}
    public Producto getProductoById(int id) { return productoRepo.obtener(id); }
    public List<Producto> getPrimerosProductos() { return productoRepo.getPrimerosProductos(loggedUser);}
    public List<Producto> getProductosByPosicion(double lat, double lon) { return productoRepo.getProductosByPosicion(lat, lon);}
    public List<Producto> getProductoReservadoEnCurso() { return productoRepo.getReservasEnCurso(loggedUser); }
    public List<Producto> getProductoReservadoEntregado() { return productoRepo.getReservasPrevias(loggedUser); }
    public List<Producto> getProductoPublicadoEnCurso() { return productoRepo.getProductoPublicadoEnCurso(loggedUser); }
    public List<Producto> getProductoPublicadoEntregado() { return productoRepo.getProductoPublicadoEntregado(loggedUser); }
    public List<Producto> getProductosPubPorUser(Usuario user) { return productoRepo.getProductosPorUsuario(user); }
    public List<Producto> getProductosSinComprar(){ return productoRepo.getProductosSinComprador(loggedUser); }
    public List<Producto> getAllProducto() { return productoRepo.obtenerTodos(); }
    public void actualizarProducto(Producto p) { productoRepo.actualizar(p);}
    public void borrarProducto(Producto producto) { productoRepo.delete(producto.getIdProducto());}

    @Override
    public List<Producto> getProductosSinVenderPorUser(Usuario user) {
        return productoRepo.getProductosSinVenderPorUser(user);
    }

    @Override
    public List<Producto> getProductoPorNombre(String nombre) {
        return productoRepo.getProductoPorNombre(nombre);
    }

    @Override
    public List<Producto> getProductosValorados(Usuario user) {
        return productoRepo.getProductosValorados(user);
    }

    /*PERSISTENCIA CATEGORIA*/
    public Categoria getCategoriaByName(String nombre) { return categoriaRepo.getCategoriaByName(nombre); }
    public List<Categoria> getAllCategorias() {return categoriaRepo.obtenerTodos();}

    @Override
    public List<Categoria> getCategoriasPorProducto(Producto producto) {
         List<ProductoCategoria> prodcats = productoCategoriaRepo.BuscarPorProducto(producto.getIdProducto());
         List<Categoria> result = new ArrayList<>();
        for (ProductoCategoria p:prodcats) {
            result.add(p.getCategoria());
        }
        return result;
    }


    /*PERSISTENCIA PRODUCTO-CATEGORIA*/
    public void guardarProductoCategoria(ProductoCategoria productoCategoria){productoCategoriaRepo.guardar2(productoCategoria);}
    public List<ProductoCategoria> getAllProductoCategoria(){return productoCategoriaRepo.obtenerTodos(); }
    public List<Producto> buscarPorCategoria(String categoria) {
        List<ProductoCategoria> prodCats = productoCategoriaRepo.BuscarPorCategoria(categoria);
        List<Producto> result = new ArrayList<>();
        for (ProductoCategoria productCat : prodCats) {
            if(productCat.getProducto().getUsuarioComprador() == null &&
            !(productCat.getProducto().getUsuarioPublicador()).equals(loggedUser)) result.add(productCat.getProducto());
        }
        return result;
    }

    /*PERSISTENCIA OPINIONES*/
    public void crearOpinion(Opinion opinion) { opinionRepo.guardar(opinion); }
    public Opinion getOpinionById(int id) { return opinionRepo.obtener(id); }
    public List<Opinion> getOpinionByUsuarioEvaluado(Usuario usuario) { return opinionRepo.getOpinionByUsuarioEvaluado(usuario); }
    public List<Opinion> getOpinionByEvaluador(Usuario usuario) { return opinionRepo.getOpinionByEvaluador(usuario); }
    public void actualizarOpinion(Opinion opinion) { opinionRepo.actualizar(opinion); }

    /*PERSISTENCIA RECETAS*/
    public void crearReceta(Receta receta) { recetaRepo.guardar(receta); }
    public Receta getRecetaById(int id) { return recetaRepo.obtener(id); }
    public List<Receta> getRecetaByChef(Usuario usuario) { return recetaRepo.getRecetaByChef(usuario); }
    public void actualizarReceta(Receta receta) { recetaRepo.actualizar(receta); }
    public void borrarReceta(Receta receta) { recetaRepo.delete(receta.getId_receta()); }

    /*VALIDACIONES*/
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
        return (!campo.equals("") );
    }
    public boolean validTime(String hora){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false); // No permitir valores inválidos (por ejemplo, 25:70 sería inválido)

        try {
            // Intentar parsear la cadena a un objeto Date
            sdf.parse(hora);
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

    public boolean isValidDate(String inputDate) {
        // Define el formato de fecha esperado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            // Intenta parsear la cadena como una fecha
            Date date = dateFormat.parse(inputDate);

            // Verifica si la fecha es igual o anterior al día actual
            Date currentDate = new Date();
            return date != null && !date.after(currentDate);

        } catch (ParseException e) {
            // La cadena no tiene el formato esperado
            return false;
        }
    }

    /*GESTION ALERTAS*/
    public void CrearAlerta(String errorString, Context contexto) {
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

    /*GESTION IMAGENES*/
    public String imagenToString(Bitmap bitmap){
        if(bitmap == null) return "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public Bitmap pasarStringAImagen(String img64){
        if(img64 == null) return null;
        byte[] imageBytes = Base64.decode(img64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    @Override
    public String valoracionAString(int valoracion) {

        String valoracionText = "";
        if(valoracion == -1 ) return "";
        for(int i = 0; i < valoracion; i++) valoracionText += "*";
        return valoracionText;
    }

    @Override
    public void pulsarPerfil(Context context, Usuario user) {
        Intent intent = new Intent(context, Perfil.class);
        intent.putExtra("usuario", user.getEmail());
        context.startActivity(intent);
    }

}
