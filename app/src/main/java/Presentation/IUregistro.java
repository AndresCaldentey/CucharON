package Presentation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;
import Negocio.*;

public class IUregistro extends AppCompatActivity {

    EditText nombre, apellido, telefono, email, direccion, password, password2;
    IService service;

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
        direccion = findViewById(R.id.direccionRegistro);
        password = findViewById(R.id.passwordRegistro);
        password2 = findViewById(R.id.passwordRegistro2);

    }

    public Usuario onClickRegistrar(View view) {
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
                        password.getText().toString(), direccion.getText().toString(),
                        Integer.parseInt(telefono.getText().toString()));

                service.crearUsuario(nuevoUser);
                return nuevoUser;
                //finish();
            }
        }
        return null;
    }

}
