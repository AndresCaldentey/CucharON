package Presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import java.io.IOException;

import Negocio.*;
import de.hdodenhof.circleimageview.CircleImageView;

public class IUregistro extends AppCompatActivity {

    EditText nombre, apellido, telefono, email, direccion, password, password2;
    CircleImageView imagenUsuario;
    TextView cambiarFoto;
    IService service;
    boolean noTieneImagen = true;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        service = Service.getService();

        nombre = findViewById(R.id.nombreRegistro);
        apellido = findViewById(R.id.apellidoRegistro);
        telefono = findViewById(R.id.telefonoRegistro);
        email = findViewById(R.id.emailRegistro);
        direccion = findViewById(R.id.edadRegistro);
        password = findViewById(R.id.passwordRegistro);
        password2 = findViewById(R.id.passwordRegistro2);
        cambiarFoto = findViewById(R.id.boton_cambiar_imagen);
        imagenUsuario = findViewById(R.id.fotoDeUsuario);
        imagenUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, REQUEST_IMAGE_CAPTURE);

            }

        });
        cambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, REQUEST_IMAGE_CAPTURE);

            }

        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            noTieneImagen = false;
            imagenUsuario.setImageBitmap(bitmap);
        }
    }

    public void onClickRegistrar(View view) {
        if(!service.validEmail(email.getText().toString())) {
            service.CrearAlerta("El email no es válido", this);
        }
        Usuario usuario = service.getUsuarioByEmail(email.getText().toString());

        if(usuario == null)
        {
            if(!service.validTel(Integer.parseInt(telefono.getText().toString()))) {
                    service.CrearAlerta("El teléfono no tiene 9 dígitos", this);
            }else if(!service.validPassword(password.getText().toString())) {
                service.CrearAlerta("La contraseña debe contener al menos 8 caracteres, 1 mayúscula y 1 número", this);
            }
            else if(!service.passwordMatch(password.getText().toString(), password2.getText().toString())) {
                service.CrearAlerta("Las contraseñas no coinciden", this);
            } else {
                //Hay que crear el usuario y añadirlo a la db
                Usuario nuevoUser = new Usuario(email.getText().toString(), nombre.getText().toString(), apellido.getText().toString(),
                        password.getText().toString(), ""/*direccion.getText().toString()*/,
                        Integer.parseInt(telefono.getText().toString()));

                service.crearUsuario(nuevoUser);
                finish();
            }
        }
    }

}
