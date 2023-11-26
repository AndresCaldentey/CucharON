package Presentation;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import com.example.cucharon.R;
import com.example.cucharon.Usuario;

import Negocio.IService;
import Negocio.Service;

public class IUModificarPerfil extends AppCompatActivity {
    EditText nombreRegistro, apellidoRegistro, telefonoRegistro, direccionRegistro, passwordRegistro, passwordRegistro2;
    IService service;
    boolean respuestaOk;
    Usuario user;
    TextView errorTv, emailRegistro;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_perfil);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        nombreRegistro = findViewById(R.id.nombreRegistro);
        apellidoRegistro = findViewById(R.id.apellidoRegistro);
        telefonoRegistro = findViewById(R.id.telefonoRegistro);
        emailRegistro = findViewById(R.id.emailRegistro);
        direccionRegistro = findViewById(R.id.direccionRegistro);
        passwordRegistro = findViewById(R.id.passwordRegistro);
        passwordRegistro2 = findViewById(R.id.passwordRegistro2);
        errorTv = findViewById(R.id.errorTv);
        service = Service.getService();
        user = service.getLoggedUser();
        //Mostrar los datos existentes
        nombreRegistro.setText(user.getNombre());
        apellidoRegistro.setText(user.getApellido());
        telefonoRegistro.setText("" + user.getTlf());
        emailRegistro.setText(user.getEmail());
        direccionRegistro.setText(user.getDireccion());

    }

    public void modificar(View v) {
        String pass = passwordRegistro.getText().toString();
        if(!pass.equals("")) {
            crearDialogo("Estás seguro que quieres cambiar la contraseña?");
            if(respuestaOk) {
                if(passwordRegistro.getText().toString() == passwordRegistro2.getText().toString()){
                    user.setContraseña(passwordRegistro.getText().toString());
                    service.actualizarUser(user);
                    finish();
                }
                else { errorTv.setText("Las contraseñas no coinciden"); }
            } else {
                errorTv.setText("");
            }
        }

        else {
            crearDialogo("Estas seguro que quieres cambiar los datos del perfil?");
            if(respuestaOk) {
                user.setNombre(nombreRegistro.getText().toString());
                user.setApellido(apellidoRegistro.getText().toString());
                user.setTlf(parseInt(telefonoRegistro.getText().toString()));
                user.setDireccion(direccionRegistro.getText().toString());
                service.actualizarUser(user);
                finish();
            }
        }
        service.setLoggedUser(user);
    }
    public void cambiarPreferencias(String email){
        SharedPreferences sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", email);
        editor.apply();
    }
    public void crearDialogo(String mensaje) {
        DialogoMensaje dialogo = new DialogoMensaje(mensaje);
        dialogo.show(getSupportFragmentManager(), "dialogo1");
        dialogo.procesarRespuesta(new DialogoMensaje.Respuestas() {
            @Override
            public void confirmar(DialogFragment dialog) {

                respuestaOk = true;

            }

            @Override
            public void cancelar(DialogFragment dialog) {

                respuestaOk = false;

            }
        });

    }
}
