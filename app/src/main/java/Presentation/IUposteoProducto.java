package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import Negocio.*;
import com.example.cucharon.Producto;
import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.time.LocalDate;
import java.util.Date;

public class IUposteoProducto extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE= 1;
    TextView addPhotoText;
    ImageView fotoPlato;
    String imagenPlatoBase64;
    EditText nombreEditText;
    EditText descripcionEditText;
    EditText precioEditText;
    EditText ingredientesEditText;
    EditText cantidadPlatosEditText;
    EditText horaPreparacionEditText;
    Button posteoBtn;
    Button addBtn;
    Button subsBtn;
    Button añadirCategoriasBtnPopup;
    Button añadirCategoriasBtnPrincipal;
    RelativeLayout popupCategorias;
    Producto producto;
    Usuario usuarioActual;
    TextView textoDireccion;
    String ubicacionSeleccionada;
    TextView horaRecogida1;
    TextView horaRecogida2;
    IService service;
    int numRacionesActuales;

    public static final int SELECCIONAR_UBICACION_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_plato);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        service = Service.getService();
        addPhotoText = findViewById(R.id.addPhotoText);
        fotoPlato = findViewById(R.id.fotoPlato);
        nombreEditText = findViewById(R.id.nombreEditText);
        descripcionEditText = findViewById(R.id.descripcionEditText);
        precioEditText = findViewById(R.id.precioEditText);
        cantidadPlatosEditText = findViewById(R.id.cantidadEditText);
        ingredientesEditText = findViewById(R.id.ingredientesEditText);
        horaPreparacionEditText = findViewById(R.id.horaPreparacionEditText);
        popupCategorias = findViewById(R.id.categoriasPopup);
        añadirCategoriasBtnPopup = findViewById(R.id.añadirCategoriasBtn);
        añadirCategoriasBtnPrincipal = findViewById(R.id.buttonAñadirCategorias);
        //popupCategorias.setVisibility(View.INVISIBLE);
        posteoBtn = findViewById(R.id.posteoBtn);
        addBtn = findViewById(R.id.btnAdd);
        subsBtn = findViewById(R.id.btnSubs);
        textoDireccion = findViewById(R.id.direccionSelecionada);
        horaRecogida1 = findViewById(R.id.horaRecogida1);
        horaRecogida2 = findViewById(R.id.horaRecogida2);
        numRacionesActuales = 1;

        Service service = Service.getService();
    }

    public void clickAddPhoto(View view){
        disparchTakePictureIntent();
    }

    private void disparchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // La imagen se capturó con éxito, ahora configura la imagen en fotoPlato
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            fotoPlato.setImageBitmap(imageBitmap);
            //Pasar imagen a String
            imagenPlatoBase64 = service.imagenToString(imageBitmap);

            //DEBERIAMOS GUARDAR LA IMAGEN EN LA BASE DE DATOS YA
        }

        if (requestCode == SELECCIONAR_UBICACION_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                ubicacionSeleccionada = data.getStringExtra("direccion");
                textoDireccion.setText(ubicacionSeleccionada);
            }
        }
    }





    public void clickPostearProducto(View view){

        String nombre = String.valueOf(nombreEditText.getText());
        String descripcion = String.valueOf(descripcionEditText.getText());
        String precioStr = String.valueOf(precioEditText.getText());
        String hora1 = String.valueOf(horaRecogida1.getText());
        String hora2 = String.valueOf(horaRecogida2.getText());
        String horaPreparacion = String.valueOf(horaPreparacionEditText.getText());
        String raciones = String.valueOf(cantidadPlatosEditText.getText());
        if(nombre.isEmpty() ){
            service.ErrorAlert("El producto ha de tener un nombre", this);
        }else if(ubicacionSeleccionada==null){
            service.ErrorAlert("Se ha de especificar una dirección de recogida", this);
        } else if (precioStr.isEmpty()) {
            service.ErrorAlert("El producto ha de tener un precio", this);
        }else if(!service.validRaciones(raciones)){
            service.ErrorAlert("El numero de raciones ha de ser entero", this);
        }else if(!service.validPrecio(precioStr)){
            service.ErrorAlert("El precio ha de ser un numero positivo y los decimales se han de indicar con un punto", this);
        }else if ( horaPreparacion.isEmpty()) {
            service.ErrorAlert("Se ha de especificar la hora de preparación", this);
        }else if(!service.validTime(horaPreparacion)){
            service.ErrorAlert("Asegúrese de que la hora de preparación es correcta y tiene el formato HH:mm", this);
        }else if ( hora1.isEmpty()) {
            service.ErrorAlert("Se ha de especificar la franja temprana de la hora de recogida", this);
        }else if(!service.validTime(hora1)){
            service.ErrorAlert("Asegúrese de que la hora de la franja temprana introducida es correcta y tiene el formato HH:mm", this);
        }else if ( hora2.isEmpty()) {
            service.ErrorAlert("Se ha de especificar la franja tardía de la hora de recogida", this);
        }else if(!service.validTime(hora2)){
            service.ErrorAlert("Asegúrese de que la hora de la franja tardía introducida es correcta y tiene el formato HH:mm", this);
        }else if(!service.validTimeRange(hora1,hora2)){
            service.ErrorAlert("La franja superior horaria ha de ser mayor que la inferior", this);
        }else if(descripcion.isEmpty()){
            service.ErrorAlert("El producto ha de tener una descripción", this);
        }else if(imagenPlatoBase64 == null){
            service.ErrorAlert("Haga una foto al producto", this);

        }else{
            // Resto del código para crear y guardar el producto
            String usuarioPublicador = service.getLoggedUser().getEmail();
            Producto producto = new Producto(1,nombre,descripcion,Double.parseDouble(precioStr),hora1+" - "+hora2,horaPreparacion,imagenPlatoBase64,ubicacionSeleccionada,numRacionesActuales, new Date(),usuarioPublicador);
            service.crearProducto(producto);
        }


    }

    public void irMapa(View view){
        Intent intent = new Intent(IUposteoProducto.this, Mapa.class);
        startActivityForResult(intent, SELECCIONAR_UBICACION_REQUEST);
        //startActivity(intent);

    }

    public void sumarRacionPlato(View view){
        numRacionesActuales = Integer.parseInt(cantidadPlatosEditText.getText().toString());
        numRacionesActuales++;
        cantidadPlatosEditText.setText(""+numRacionesActuales);
    }
    public void restarRacionPlato(View view){
        numRacionesActuales = Integer.parseInt(cantidadPlatosEditText.getText().toString());
        if(numRacionesActuales>1){
        numRacionesActuales--;
        cantidadPlatosEditText.setText(""+numRacionesActuales);
        }
    }

    public void añadirCategoriasPrincipalOnClick(View view){
        popupCategorias.setVisibility(View.VISIBLE);
    }

    public void añadirCategoriasPopupOnClick(View view){
        popupCategorias.setVisibility(View.INVISIBLE);
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

}
