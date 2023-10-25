package Presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;
import Negocio.*;
import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class IUregistro extends AppCompatActivity {

    EditText nombre, apellido, telefono, email, direccion, password, password2;
    Button btnRegistro;
    UsuarioRepository userRepo;
    IService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        userRepo = new UsuarioRepository(SingletonConnection.getSingletonInstance());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        service = new Servicio();
        nombre = findViewById(R.id.nombreRegistro);
        apellido = findViewById(R.id.apellidoRegistro);
        telefono = findViewById(R.id.telefonoRegistro);
        email = findViewById(R.id.emailRegistro);
        direccion = findViewById(R.id.direccionRegistro);
        btnRegistro = findViewById(R.id.btnRegistro);
        password = findViewById(R.id.passwordRegistro);
        password2 = findViewById(R.id.passwordRegistro2);

    }

    public void onClickRegistrar(View view) {
        //Comprobar que no exista un usuario con ese email, etc
        Handler handler = new Handler(Looper.getMainLooper());

            if(!service.validEmail(email.getText().toString())) {
                service.ErrorAlert("El email no es válido", this);
            }
            Thread hilo = new Thread(() -> {
            Usuario usuario = userRepo.getUserByEmail(email.getText().toString());
            if (usuario == null) {
                if(!service.validTel(Integer.parseInt(telefono.getText().toString()))) {
                    handler.post(() -> {
                        service.ErrorAlert("El teléfono no tiene 9 dígitos", this);
                    });

                }
                else if(!service.validPassword(password.getText().toString())) {
                    handler.post(() -> {
                        service.ErrorAlert("La contraseña debe contener al menos 8 caracteres, 1 mayúscula y 1 número", this);
                    });

                }
                else if(!service.passwordMatch(password.getText().toString(), password2.getText().toString())) {
                    handler.post(() -> {
                        service.ErrorAlert("Las contraseñas no coinciden", this);
                    });

                }else {

                    //Hay que crear el usuario y añadirlo a la db
                    Usuario nuevoUser = new Usuario(email.getText().toString(), nombre.getText().toString(), apellido.getText().toString(),
                            password.getText().toString(), direccion.getText().toString(),
                            Integer.parseInt(telefono.getText().toString()));

                    userRepo.guardar(nuevoUser);
                    finish();
                }
            }
            else { handler.post(() -> {service.ErrorAlert("El email está en uso", this);}); }


        });
        hilo.start();

    }





}
