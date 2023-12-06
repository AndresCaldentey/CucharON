package Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import Negocio.*;

import com.example.cucharon.Categoria;
import com.example.cucharon.Producto;
import com.example.cucharon.ProductoCategoria;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IUposteoProducto extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1, SELECCIONAR_UBICACION_REQUEST = 2;
    private static List<Categoria> todasLasCategorias;
    private IService service;
    private RelativeLayout popupCategorias;
    private LinearLayout linearLayoutCategorias, linLayCategoriasSeleccionadas;
    private EditText nombreEditText, descripcionEditText, precioEditText, cantidadPlatosEditText, horaPreparacionEditText;
    private TextView textoDireccion, horaRecogida1, horaRecogida2;
    private ImageView fotoPlato;
    private LatLng posicionProducto;
    private List<Categoria> categoriasProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_plato);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        service = Service.getService();
        fotoPlato = findViewById(R.id.fotoPlato);
        nombreEditText = findViewById(R.id.tarjetaEditText);
        descripcionEditText = findViewById(R.id.descripcionEditText);
        precioEditText = findViewById(R.id.precioEditText);
        cantidadPlatosEditText = findViewById(R.id.cantidadEditText);
        horaPreparacionEditText = findViewById(R.id.horaPreparacionEditText);
        popupCategorias = findViewById(R.id.categoriasPopup);
        linearLayoutCategorias = findViewById(R.id.linLayoutCategorias);
        linLayCategoriasSeleccionadas = findViewById(R.id.linearLayCategoriasPrincipal);
        textoDireccion = findViewById(R.id.direccionSelecionada);
        horaRecogida1 = findViewById(R.id.horaRecogida1);
        horaRecogida2 = findViewById(R.id.horaRecogida2);

        categoriasProducto = new ArrayList<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Se ha obtenido resultado de la actividad seleccionar imagen
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                // Ahora 'bitmap' contiene la imagen en formato Bitmap y puedes usarlo como desees.
            } catch (IOException e) {
                e.printStackTrace();
                // Maneja la excepción apropiadamente
            }

            fotoPlato.setImageBitmap(bitmap);
        }

        //Se ha obtenido resultado de la actividad seleccionar ubicacion
        if (requestCode == SELECCIONAR_UBICACION_REQUEST && resultCode == RESULT_OK && data != null) {
            textoDireccion.setText(data.getStringExtra("direccion"));
            double lat = data.getDoubleExtra("latitud", 2);
            double lon = data.getDoubleExtra("longitud", 2);
            posicionProducto = new LatLng(lat, lon);
        }
    }

    public void clickAddPhoto(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pickImageIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pickImageIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void clickPostearProducto(View view) {
        //Se recogen la informacion proporcionada para el nuevo producto
        String nombre = String.valueOf(nombreEditText.getText());
        String descripcion = String.valueOf(descripcionEditText.getText());
        double precio = Double.parseDouble(precioEditText.getText().toString());
        String hora1 = String.valueOf(horaRecogida1.getText());
        String hora2 = String.valueOf(horaRecogida2.getText());
        String horaPreparacion = String.valueOf(horaPreparacionEditText.getText());
        int raciones = Integer.parseInt(cantidadPlatosEditText.getText().toString());
        String imagen = service.imagenToString(((BitmapDrawable) fotoPlato.getDrawable()).getBitmap());
        String direccion = textoDireccion.getText().toString();

        //Se crea el producto
        Producto producto = new Producto(98, nombre, descripcion, precio, hora1 + " - " + hora2, horaPreparacion, imagen,
                direccion, raciones, new Date(), service.getLoggedUser(), posicionProducto.latitude, posicionProducto.longitude);

        //Si se valida el producto se guarda en la BD
        if (validarProducto(producto, hora1, hora2)) {
            service.crearProducto(producto);    //Guarda en la bd el nuevo producto
            Producto productoPosteado = service.getProductoById(producto.getIdProducto());

            //Agrega tambien a la bd todas las categorias del producto
            for (Categoria categoria : categoriasProducto) {
                ProductoCategoria productoCategoria = new ProductoCategoria(productoPosteado, categoria);
                service.guardarProductoCategoria(productoCategoria);
            }
        }

    }

    private boolean validarProducto(Producto producto, String hora1, String hora2) {
        if (producto.getNombre().isEmpty()) {
            service.CrearAlerta("El producto ha de tener un nombre", this);
            return false;
        } else if (producto.getDireccionRecogida() == null) {
            service.CrearAlerta("Se ha de especificar una dirección de recogida", this);
            return false;
        } else if (producto.getPrecio() == null) {
            service.CrearAlerta("El producto ha de tener un precio", this);
            return false;
        } else if (!service.validRaciones(producto.getNumRaciones() + "")) {
            service.CrearAlerta("El numero de raciones ha de ser entero", this);
            return false;
        } else if (!service.validPrecio(producto.getPrecio() + "")) {
            service.CrearAlerta("El precio ha de ser un numero positivo y los decimales se han de indicar con un punto", this);
            return false;
        } else if (producto.getHoraPreparacion().isEmpty()) {
            service.CrearAlerta("Se ha de especificar la hora de preparación", this);
            return false;
        } else if (!service.validTime(producto.getHoraPreparacion())) {
            service.CrearAlerta("Asegúrese de que la hora de preparación es correcta y tiene el formato HH:mm", this);
            return false;
        } else if (hora1.isEmpty()) {
            service.CrearAlerta("Se ha de especificar la franja temprana de la hora de recogida", this);
            return false;
        } else if (!service.validTime(hora1)) {
            service.CrearAlerta("Asegúrese de que la hora de la franja temprana introducida es correcta y tiene el formato HH:mm", this);
            return false;
        } else if (hora2.isEmpty()) {
            service.CrearAlerta("Se ha de especificar la franja tardía de la hora de recogida", this);
            return false;
        } else if (!service.validTime(hora2)) {
            service.CrearAlerta("Asegúrese de que la hora de la franja tardía introducida es correcta y tiene el formato HH:mm", this);
            return false;
        } else if (!service.validTimeRange(hora1, hora2)) {
            service.CrearAlerta("La franja superior horaria ha de ser mayor que la inferior", this);
            return false;
        } else if (producto.getContenido().isEmpty()) {
            service.CrearAlerta("El producto ha de tener una descripción", this);
            return false;
        } else if (producto.getImagen().isEmpty()) {
            service.CrearAlerta("Haga una foto al producto", this);
            return false;
        }

        return true;
    }

    public void irMapa(View view) {
        Intent intent = new Intent(IUposteoProducto.this, Mapa.class);
        startActivityForResult(intent, SELECCIONAR_UBICACION_REQUEST);
        //startActivity(intent);
    }

    public void sumarRacionPlato(View view) {
        int racionesActuales = Integer.parseInt(cantidadPlatosEditText.getText().toString());
        racionesActuales++;
        cantidadPlatosEditText.setText(racionesActuales);
    }

    public void restarRacionPlato(View view) {
        int racionesActuales = Integer.parseInt(cantidadPlatosEditText.getText().toString());
        if (racionesActuales > 1) {
            racionesActuales--;
            cantidadPlatosEditText.setText(racionesActuales);
        }
    }

    public void añadirCategoriasPrincipalOnClick(View view) {
        categoriasProducto.clear();
        popupCategorias.setVisibility(View.VISIBLE);
        //mostrarCategoriasEnPopup();
        todasLasCategorias = service.getAllCategorias();
        Context context = getApplicationContext();

        for (Categoria categoria : todasLasCategorias) {
            CheckBox categoriaCheckBox = new CheckBox(context);
            categoriaCheckBox.setText(categoria.getNombre());

            linearLayoutCategorias.addView(categoriaCheckBox);
        }
    }

    public void añadirCategoriasPopupOnClick(View view) {
        for (int i = 0; i < linearLayoutCategorias.getChildCount(); i++) {
            View child = linearLayoutCategorias.getChildAt(i);

            if (child instanceof CheckBox) {    // Verificar si el primer hijo es un CheckBox
                CheckBox checkBox = (CheckBox) child;

                if (checkBox.isChecked()) {     // Ahora puedes verificar si el CheckBox está seleccionado
                    categoriasProducto.add(todasLasCategorias.get(i));
                }
            }
        }
        popupCategorias.setVisibility(View.INVISIBLE);
        linLayCategoriasSeleccionadas.removeAllViews();

        Context context = getApplicationContext();
        for (Categoria categoria : categoriasProducto) {
            Button categoriaSeleccionadaBtn = new Button(context);
            categoriaSeleccionadaBtn.setText(categoria.getNombre());
            linLayCategoriasSeleccionadas.addView(categoriaSeleccionadaBtn);
        }
    }

    public void buscarOnClick(View view) {
        Intent intent = new Intent(IUposteoProducto.this, IUbuscar.class);
        startActivity(intent);
        finish();
    }

    public void sugerenciasOnClick(View view) {
        Intent intent = new Intent(IUposteoProducto.this, IUsugerencias.class);
        startActivity(intent);
        finish();
    }

    public void perfilOnClick(View view) {
        Intent intent = new Intent(IUposteoProducto.this, IUperfil.class);
        startActivity(intent);
        finish();
    }

    public Producto posteoSinImagenNiMapa(View view) {
        //Se recogen la informacion proporcionada para el nuevo producto
        String nombre = String.valueOf(nombreEditText.getText());
        String descripcion = String.valueOf(descripcionEditText.getText());
        double precio = Double.parseDouble(precioEditText.getText().toString());
        String hora1 = String.valueOf(horaRecogida1.getText());
        String hora2 = String.valueOf(horaRecogida2.getText());
        String horaPreparacion = String.valueOf(horaPreparacionEditText.getText());
        int raciones = Integer.parseInt(cantidadPlatosEditText.getText().toString());
        String direccion = textoDireccion.getText().toString();

        //Se crea el producto

        Usuario user = new Usuario("a@gmail.com", "usuarioPureba", "apellido", "contraseña", "direccion", 123);

        service.crearUsuario(user);
        Producto producto = new Producto(98, nombre, descripcion, precio, hora1 + " - " + hora2, horaPreparacion, "prueba", "direccion prueba", raciones, new Date(), user, 0.000, 0.000);

        if (producto.getNombre().isEmpty()) {
            return null;
        } else if (producto.getDireccionRecogida() == null) {
            return null;
        } else if (producto.getPrecio() == null) {
            return null;
        } else if (!service.validRaciones(producto.getNumRaciones() + "")) {
            return null;
        } else if (!service.validPrecio(producto.getPrecio() + "")) {
            return null;
        } else if (producto.getHoraPreparacion().isEmpty()) {
            return null;
        } else if (!service.validTime(producto.getHoraPreparacion())) {
            return null;
        } else if (hora1.isEmpty()) {
            return null;
        } else if (!service.validTime(hora1)) {
            return null;
        } else if (hora2.isEmpty()) {
            return null;
        } else if (!service.validTime(hora2)) {
            return null;
        } else if (!service.validTimeRange(hora1, hora2)) {
            return null;
        } else if (producto.getContenido().isEmpty()) {
            return null;
        } else if (producto.getImagen().isEmpty()) {
            return null;
        }

        service.crearProducto(producto);    //Guarda en la bd el nuevo producto
        Producto productoPosteado = service.getProductoById(producto.getIdProducto());


        return producto;
    }


}
