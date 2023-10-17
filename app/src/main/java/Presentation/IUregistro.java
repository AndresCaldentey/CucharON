package Presentation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class IUregistro extends AppCompatActivity {

    EditText nombre, apellido, telefono, email, direccion, password;
    Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nombre = findViewById(R.id.nombreRegistro);
        apellido = findViewById(R.id.apellidoRegistro);
        telefono = findViewById(R.id.telefonoRegistro);
        email = findViewById(R.id.emailRegistro);
        direccion = findViewById(R.id.direccionRegistro);
        btnRegistro = findViewById(R.id.btnRegistro);
        password = findViewById(R.id.passwordRegistro);

    }

    public void onClickRegistrar(View view)
    {
        //Comprobar que no exista un usuario con ese email, etc
        Thread hilo = new Thread(() -> {

            Usuario usuario = new UsuarioRepository(SingletonConnection.getSingletonInstance()).getUserByEmail(email.getText().toString());
            if(usuario == null)
            {
                //Hay que crear el usuario y añadirlo a la db
                Usuario nuevoUser = new Usuario(email.getText().toString(), nombre.getText().toString(), apellido.getText().toString(),
                        password.getText().toString(), direccion.getText().toString(),
                        Integer.parseInt(telefono.getText().toString()));

                new UsuarioRepository(SingletonConnection.getSingletonInstance()).guardar(nuevoUser);
            }
            finish();

        });
        hilo.start();

    }

}
