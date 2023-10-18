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

import Persistencia.SingletonConnection;
import Persistencia.UsuarioRepository;

public class IUregistro extends AppCompatActivity {

    EditText nombre, apellido, telefono, email, direccion, password, password2;
    Button btnRegistro;
    UsuarioRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        userRepo = new UsuarioRepository(SingletonConnection.getSingletonInstance());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

            if(!validEmail(email.getText().toString())) {
                ErrorAlert("El email no es válido");
            }
            Thread hilo = new Thread(() -> {
            Usuario usuario = userRepo.getUserByEmail(email.getText().toString());
            if (usuario == null) {
                if(!validTel(Integer.parseInt(telefono.getText().toString()))) {
                    handler.post(() -> {
                        ErrorAlert("El teléfono no tiene 9 dígitos");
                    });

                }
                else if(!validPassword(password.getText().toString())) {
                    handler.post(() -> {
                        ErrorAlert("La contraseña debe contener al menos 8 caracteres, 1 mayúscula y 1 número");
                    });

                }
                else if(!passwordMatch(password.getText().toString(), password2.getText().toString())) {
                    handler.post(() -> {
                        ErrorAlert("Las contraseñas no coinciden");
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
            else { handler.post(() -> {ErrorAlert("El email está en uso");}); }


        });
        hilo.start();

    }

    public boolean validTel(int tel) {
        String numeroComoString = Integer.toString(tel);
        int cantidadDigitos = numeroComoString.length();
        return cantidadDigitos == 9;
    }

    public boolean validEmail(String email) {
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

    public void ErrorAlert(String errorString) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
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


}
