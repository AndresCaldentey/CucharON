package UserInteface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.cucharon.Manifest;
import com.example.cucharon.R;

import java.io.ByteArrayOutputStream;

public class PosteoProducto extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE=1;
    TextView addPhotoText;
    ImageView fotoPlato;
    String imagenPlatoBase64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_plato);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        addPhotoText = findViewById(R.id.addPhotoText);
        fotoPlato = findViewById(R.id.fotoPlato);
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


}