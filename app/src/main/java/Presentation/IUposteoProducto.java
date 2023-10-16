package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;


import com.example.cucharon.Producto;
import com.example.cucharon.R;

import java.io.ByteArrayOutputStream;

import Persistencia.ProductoRepository;
import Persistencia.SingletonConnection;

public class IUposteoProducto extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE=1;
    TextView addPhotoText;
    ImageView fotoPlato;
    String imagenPlatoBase64;
    EditText nombreEditText;
    EditText descripcionEditText;
    EditText precioEditText;
    EditText ingredientesEditText;
    Button posteoBtn;
    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_plato);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        addPhotoText = findViewById(R.id.addPhotoText);
        fotoPlato = findViewById(R.id.fotoPlato);
        nombreEditText = findViewById(R.id.nombreEditText);
        descripcionEditText = findViewById(R.id.precioEditText);
        precioEditText = findViewById(R.id.precioEditText);
        ingredientesEditText = findViewById(R.id.ingredientesEditText);
        posteoBtn = findViewById(R.id.posteoBtn);
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
            imagenPlatoBase64 = imagenToString(imageBitmap);

            //DEBERIAMOS GUARDAR LA IMAGEN EN LA BASE DE DATOS YA
        }
    }

    private String imagenToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    //PROCESO CONTRARIO PARA CUANDO LO NECESITEMOS AL MOSTRAR LOS PRODUCTOS

      /*private void pasarDeStringAImagen(){
        // Supongamos que tienes la cadena Base64 almacenada en la variable "imageBase64"
        String imageBase64 = "tu_cadena_base64"; // Reemplaza con tu cadena Base64

        // Decodifica la cadena Base64 en un arreglo de bytes
        byte[] imageBytes = Base64.decode(imageBase64, Base64.DEFAULT);

        // Crea un objeto Bitmap a partir de los bytes decodificados
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        // Configura el Bitmap en un ImageView (reemplaza "imageView" con tu ImageView)
        imageView.setImageBitmap(decodedImage);
    }*/

    public void clickPostearProducto(View view){
        String nombre = String.valueOf(nombreEditText.getText());
        String descripcion = String.valueOf(descripcionEditText.getText());
        float precio = Float.parseFloat(String.valueOf(precioEditText.getText()));
        String direccion = "";//CONSEGUIR
        //INGREDIENTES?????
        String usuarioPublicador=""; //SETEAR AL ID DEL USUARIO DE LA SESION
        producto = new Producto(1,nombre,descripcion,precio,imagenPlatoBase64,direccion,usuarioPublicador);

        new ProductoRepository(SingletonConnection.getSingletonInstance()).guardar(producto);

    }
    public void buscarOnClick(View view) {
        Intent intent = new Intent(IUposteoProducto.this, IUbuscar.class);
        startActivity(intent);
    }
    public void sugerenciasOnClick(View view) {
        Intent intent = new Intent(IUposteoProducto.this, IUsugerencias.class);
        startActivity(intent);
    }
    public void perfilOnClick(View view) {
        Intent intent = new Intent(IUposteoProducto.this, IUperfil.class);
        startActivity(intent);
    }

}
